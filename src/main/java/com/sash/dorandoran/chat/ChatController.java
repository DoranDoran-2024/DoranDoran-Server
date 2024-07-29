package com.sash.dorandoran.chat;

import com.sash.dorandoran.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/pronunciation/similarity")
    public ResponseDto<Integer> getScore(@RequestBody List<ChatRequest> request) {
        return ResponseDto.onSuccess(chatService.getChatCompletion(request));
    }

}
