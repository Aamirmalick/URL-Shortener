package com.aamirtechie.URLShortener.dto.request;

import com.aamirtechie.URLShortener.enums.URLShortenerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class URLShortenerRequest {

    private String originalUrl;
    private String customAlias;
    private String expirationDate;
    @NotNull(message = "Status is required.")
    private URLShortenerStatus status;
}
