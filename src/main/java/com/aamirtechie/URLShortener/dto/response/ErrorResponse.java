package com.aamirtechie.URLShortener.dto.response;

import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@ToString
public class ErrorResponse {
    private final String statusCode;
    private final HttpStatus httpStatus;
    private final String requestId;
    private final Instant createdAt;
    private final String message;
    private final List<String> details;
    private final String path;
    private final String method;
    private final String serviceName;
    private final String exceptionType;
    private final String debugMessage;

    public ErrorResponse(String statusCode, HttpStatus httpStatus, String requestId, Instant createdAt,
                         String message, List<String> details, String path, String method,
                         String serviceName, String exceptionType, String debugMessage) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.requestId = requestId;
        this.createdAt = createdAt;
        this.message = message;
        this.details = details;
        this.path = path;
        this.method = method;
        this.serviceName = serviceName;
        this.exceptionType = exceptionType;
        this.debugMessage = debugMessage;
    }

    public String getStatusCode() { return statusCode; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getRequestId() { return requestId; }
    public Instant getCreatedAt() { return createdAt; }
    public String getMessage() { return message; }
    public List<String> getDetails() { return details; }
    public String getPath() { return path; }
    public String getMethod() { return method; }
    public String getServiceName() { return serviceName; }
    public String getExceptionType() { return exceptionType; }
    public String getDebugMessage() { return debugMessage; }
}
