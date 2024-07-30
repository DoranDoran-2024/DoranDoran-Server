package com.sash.dorandoran.user.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AttendanceResponse {

    private List<Boolean> attendanceDays;

    @Builder
    public AttendanceResponse(List<Boolean> attendanceDays) {
        this.attendanceDays = attendanceDays;
    }
}
