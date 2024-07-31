package com.sash.dorandoran.chat.presentation;

import com.sash.dorandoran.chat.implement.ChatService;
import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "💬 Chat API")
@RestController
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseDto<Boolean> createChatRoom(@AuthUser User user) {
        return ResponseDto.onSuccess(chatService.createChatRoom(user));
    }


}
