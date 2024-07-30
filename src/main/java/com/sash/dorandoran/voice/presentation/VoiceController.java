package com.sash.dorandoran.voice.presentation;

import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.voice.implement.ClovaVoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/voices")
@Tag(name = "🎶 Voice API")
@RestController
public class VoiceController {

    private final ClovaVoiceService clovaVoiceService;

    @GetMapping("/synthesize")
    public ResponseDto<String> synthesizeAndUpload(@RequestParam String speaker,
                                                   @RequestParam String text,
                                                   @RequestParam String format) {
        return ResponseDto.onSuccess(clovaVoiceService.synthesizeSpeechAndUpload(speaker, text, format));
    }
}