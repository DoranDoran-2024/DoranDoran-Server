package com.sash.dorandoran.feign.config;

import com.sash.dorandoran.feign.dto.ChatCompletionResponse;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventStreamHttpMessageConverter extends AbstractHttpMessageConverter<ChatCompletionResponse> {

    private final ObjectMapper objectMapper;

    public EventStreamHttpMessageConverter(ObjectMapper objectMapper) {
        super(new MediaType("text", "event-stream"));
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ChatCompletionResponse.class.isAssignableFrom(clazz);
    }

    @Override
    protected ChatCompletionResponse readInternal(Class<? extends ChatCompletionResponse> clazz, HttpInputMessage inputMessage) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputMessage.getBody(), StandardCharsets.UTF_8);
        return objectMapper.readValue(reader, clazz);
    }

    @Override
    protected void writeInternal(ChatCompletionResponse response, HttpOutputMessage outputMessage) {
        // Writing is not supported in this converter
        throw new UnsupportedOperationException("Writing not supported");
    }
}
