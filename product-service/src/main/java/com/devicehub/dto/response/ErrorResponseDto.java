package com.devicehub.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {

    private String apiPath;
    private HttpStatusCode errorCode;
    private String errorMsg;
    private LocalDateTime errorTime;
}
