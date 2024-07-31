package com.sash.dorandoran.chat.presentation;

import com.sash.dorandoran.chat.implement.ChatService;
import com.sash.dorandoran.chat.presentation.dto.ChatRequest;
import com.sash.dorandoran.chat.presentation.dto.ChatResponse;
import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "💬 Chat API")
@RestController
public class ChatController {

    private final ChatService chatService;

    @Operation(
            summary = "🔑 [오늘의 대화] 채팅 생성",
            description = "채팅방을 생성합니다."
    )
    @PostMapping
    public ResponseDto<Long> createChatRoom(@AuthUser User user) {
        return ResponseDto.onSuccess(chatService.createChatRoom(user).getId());
    }

    @Operation(
            summary = "🔑 [오늘의 대화] 채팅 메시지 생성",
            description = "채팅 메시지를 생성합니다."
    )
    @PostMapping("/{chatRoomId}")
    public ResponseDto<ChatResponse> createChatMessage(@AuthUser User user, @PathVariable Long chatRoomId, @RequestBody ChatRequest request) {
        return ResponseDto.onSuccess(chatService.createChatMessage(user, chatRoomId, request));
    }

    @Operation(
            summary = "🔑 [오늘의 대화] 채팅 내역 요약",
            description = "채팅 내역을 요약합니다."
    )
    @PostMapping("/{chatRoomId}/summary")
    public ResponseDto<ChatResponse> summaryChat(@AuthUser User user, @PathVariable Long chatRoomId) {
        return ResponseDto.onSuccess(chatService.summarizeChatRoom(user, chatRoomId));
    }

}
