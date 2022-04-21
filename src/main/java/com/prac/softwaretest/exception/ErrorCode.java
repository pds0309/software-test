package com.prac.softwaretest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    NOT_FOUND(404, "NOTFOUD : 요청한 자원을 찾을 수 없음"),
    CONFLICTED_REQUEST(409, "CONFILICTED : 서버가 받아들일 수 없는 요청"),
    BAD_REQ(400, "BAD_REQUEST : 잘못된 요청입니다.");

    private final int code;
    private final String message;

}
