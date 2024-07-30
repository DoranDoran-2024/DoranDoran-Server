package com.sash.dorandoran.feign.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NicknameResponse {

    private List<String> words = new ArrayList<>();
    private String seed;

}