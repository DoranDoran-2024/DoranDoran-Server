package com.sash.dorandoran.common.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseStatus {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 에러, 담당자에게 문의 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 4000, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4001, "로그인이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 4002, "금지된 요청입니다."),

    // User 4100 ~ 4150
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 4100, "존재하지 않는 회원입니다."),
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, 4101, "중복된 회원 이름입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}