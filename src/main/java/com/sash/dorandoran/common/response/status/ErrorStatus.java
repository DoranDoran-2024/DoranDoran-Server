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

    // Auth (4050 ~ 4051)
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, 4050, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, 4051, "만료된 토큰입니다."),
    TOKEN_UNSUPPORTED(HttpStatus.BAD_REQUEST, 4052, "지원되지 않는 토큰 형식입니다."),
    TOKEN_CLAIMS_EMPTY(HttpStatus.BAD_REQUEST, 4053, "토큰 클레임이 비어있습니다."),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST,4054, "유효하지 않은 회원 정보입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 4055, "존재하지 않는 회원입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, 4056, "비밀번호가 일치하지 않습니다."),
    AUTHENTICATION_REQUIRED(HttpStatus.BAD_REQUEST, 4057, "인증 정보가 필요합니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 4058, "저장소에 해당 토큰이 존재하지 않습니다."),

    // User 4100 ~ 4150
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 4100, "존재하지 않는 회원입니다."),
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, 4101, "중복된 회원 이름입니다."),
    USER_LEVEL_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, 4102, "유효하지 않은 점수 범위입니다."),

    // Lesson + Exercise 4151 ~ 4200
    EXERCISE_NOT_FOUND(HttpStatus.NOT_FOUND, 4100, "존재하지 않는 문제입니다."),
    LESSON_NOT_FOUND(HttpStatus.NOT_FOUND, 4101, "존재하지 않는 수업입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}