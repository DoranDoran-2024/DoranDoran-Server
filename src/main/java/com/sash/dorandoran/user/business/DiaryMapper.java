package com.sash.dorandoran.user.business;

import com.sash.dorandoran.chat.domain.ChatRoom;
import com.sash.dorandoran.user.presentation.dto.DiarySummaryListResponse;
import com.sash.dorandoran.user.presentation.dto.DiarySummaryResponse;

import java.util.List;

public class DiaryMapper {

    public static DiarySummaryListResponse toDiarySummaryListResponse(List<ChatRoom> chatRooms) {
        List<DiarySummaryResponse> diaries = chatRooms.stream()
                .map(DiaryMapper::toDiarySummaryResponse)
                .filter(diary -> diary.getContent() != null)
                .toList();
        return DiarySummaryListResponse.builder()
                .diaries(diaries)
                .build();
    }

    public static DiarySummaryResponse toDiarySummaryResponse(ChatRoom chatRoom) {
        return DiarySummaryResponse.builder()
                .content(chatRoom.getSummary())
                .date(chatRoom.getCreatedAt().toLocalDate())
                .build();
    }

}
