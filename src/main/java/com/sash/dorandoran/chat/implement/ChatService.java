package com.sash.dorandoran.chat.implement;

import com.sash.dorandoran.chat.dao.ChatMessageRepository;
import com.sash.dorandoran.chat.dao.ChatRoomRepository;
import com.sash.dorandoran.chat.domain.ChatMessage;
import com.sash.dorandoran.chat.domain.ChatRoom;
import com.sash.dorandoran.chat.presentation.dto.ChatRequest;
import com.sash.dorandoran.chat.presentation.dto.ChatResponse;
import com.sash.dorandoran.feign.client.ClovaStudioClient;
import com.sash.dorandoran.feign.dto.ChatCompletionRequest;
import com.sash.dorandoran.feign.properties.ChatCompletionProperties;
import com.sash.dorandoran.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ClovaStudioClient clovaStudioClient;
    private final ChatCompletionProperties chatCompletionProperties;

    @Value("${chat-creation-prompt}")
    private String prompt;

    @Transactional
    public boolean createChatRoom(User user) {
        ChatRoom chatRoom = buildChatRoom(user);
        chatRoomRepository.save(chatRoom);
        return true;
    }

    private ChatRoom buildChatRoom(User user) {
        return ChatRoom.builder()
                .user(user)
                .build();
    }

    @Transactional
    public ChatResponse createChatMessage(User user, Long chatRoomId, ChatRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid chat room ID"));

        StringBuilder sb = new StringBuilder();
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomId);
        for(ChatMessage chatMessage: chatMessages) {
            sb.append(chatMessage.getContent()).append("\n");
        }
        sb.append(request.getContent());

        String generatedContent = generateContent(sb.toString());
        chatMessageRepository.save(buildChatMessage(request.getContent(), chatRoom));
        chatMessageRepository.save(buildChatMessage(generatedContent, chatRoom));
        return ChatResponse.builder().content(generatedContent).build();
    }

    private String generateContent(String content) {
        ChatCompletionRequest.Message systemMessage = ChatCompletionRequest.Message.builder()
                .role("system")
                .content(prompt)
                .build();

        ChatCompletionRequest.Message userMessage = ChatCompletionRequest.Message.builder()
                .role("user")
                .content(content)
                .build();

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(Arrays.asList(systemMessage, userMessage))
                .topP(0.8)
                .topK(0)
                .maxTokens(256)
                .temperature(0.5)
                .repeatPenalty(5.0)
                .stopBefore(List.of())
                .includeAiFilters(true)
                .seed(0)
                .build();

        return clovaStudioClient.getChatCompletion(
                chatCompletionProperties.getApiKey(),
                chatCompletionProperties.getApigwKey(),
                chatCompletionProperties.getRequestId(),
                chatCompletionRequest).getResult().getMessage().getContent();
    }

    private ChatMessage buildChatMessage(String content, ChatRoom chatRoom) {
        return ChatMessage.builder()
                .content(content)
                .chatRoom(chatRoom)
                .build();
    }

}
