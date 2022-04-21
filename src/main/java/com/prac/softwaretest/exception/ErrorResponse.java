package com.prac.softwaretest.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @JsonProperty("errorInfo")
    private ErrorCode errorCode;

    @JsonProperty("errorDetails")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String details;

}
