package com.sash.dorandoran.chat.implement;

import com.sash.dorandoran.chat.dao.ChatRoomRepository;
import com.sash.dorandoran.chat.domain.ChatRoom;
import com.sash.dorandoran.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

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

}
