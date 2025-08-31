package com.aamirtechie.URLShortener.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@ToString
public class CommonResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private final Instant timestamp;
    private final String statusCode;
    private final String apiName;
    private final String requestId;
    private final String httpStatus;
    private final String message;
    private final List<String> errors;
    private final Long processingTimeMs;
    private final String serviceVersion;
    private final String path;
    private final String method;
    private final Object data;
}
