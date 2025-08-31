package com.aamirtechie.URLShortener.service.impl;

import com.aamirtechie.URLShortener.constant.URLShortenerConstant;
import com.aamirtechie.URLShortener.dto.request.URLShortenerRequest;
import com.aamirtechie.URLShortener.dto.response.CommonResponse;
import com.aamirtechie.URLShortener.enums.URLShortenerStatus;
import com.aamirtechie.URLShortener.model.ShortenedUrl;
import com.aamirtechie.URLShortener.repository.ShortenedUrlRepository;
import com.aamirtechie.URLShortener.service.URLShortenerService;
import com.aamirtechie.URLShortener.util.ShortUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
@Slf4j
public class URLShortenerServiceImpl implements URLShortenerService {

    private final ShortenedUrlRepository shortenedUrlRepository;

    public URLShortenerServiceImpl(ShortenedUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    /**
     * This is a placeholder implementation of the URLShortenerService.
     * In a real application, this would contain logic to shorten URLs,
     * retrieve long URLs, delete shortened URLs, update them, and check their existence.
     */

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<String>> shortenURL(URLShortenerRequest urlShortenerRequest) {
        log.info("Received request to shorten URL: {}", urlShortenerRequest.toString());
        ShortenedUrl shortenedUrl = shortenedUrlBuilder(urlShortenerRequest);

        log.info("Shortened URL created successfully: {}", shortenedUrl);
        String body = "Shortened URL created: " + shortenedUrl.getShortCodeUrl();
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body(body));
    }

    private ShortenedUrl shortenedUrlBuilder(URLShortenerRequest urlShortenerRequest) {
        log.info("Building shortened URL for original URL: {}", urlShortenerRequest);
        ShortenedUrl shortenedUrl = ShortenedUrl.builder().originalUrl(urlShortenerRequest.getOriginalUrl()).shortCodeUrl(URLShortenerConstant.URL + generateShortenedUrl()).status(urlShortenerRequest.getStatus()).build();
        log.info("Shortened URL created: {}", shortenedUrl.getShortCodeUrl());
        shortenedUrlRepository.save(shortenedUrl);
        return shortenedUrl;
    }

    private String generateShortenedUrl() {
        return Stream.generate(ShortUrlGenerator::generateShortCodeUrl).filter(code -> !checkIfShortenedUrlExists(code)).findFirst().orElseThrow();
    }

    private boolean checkIfShortenedUrlExists(String shortUrl) {
        log.info("Checking if shortened URL exists: {}", shortUrl);
        return shortenedUrlRepository.existsByShortCodeUrl(shortUrl);
    }

    /**
     * This method is used to redirect to the original URL based on the short URL provided.
     * It checks if the short URL exists in the database and returns the original URL if found.
     *
     * @param shortUrl The short URL to redirect to the original URL.
     * @return ResponseEntity containing the original URL or an error message if not found.
     */
    @Override
    public ResponseEntity<?> redirectToOrginaURL(String shortUrl) {
        // Log the request for redirecting to the original URL
        log.info("Received request to redirect to original URL for short URL: {}", shortUrl);

        // Start the timer to measure processing time
        Instant start = Instant.now();
        log.info("URL shortening process started at {}", start);

        // Validate the short URL format
        if (shortUrl == null || shortUrl.isEmpty() || !shortUrl.contains(URLShortenerConstant.HTTPS) || !shortUrl.contains(URLShortenerConstant.HTTP)) {
            log.error("Invalid short URL format: {}", shortUrl);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid short URL format");
        }

        //checking if the short URL exists
        Optional<ShortenedUrl> shortenedUrl = shortenedUrlRepository.findByShortCodeUrl(shortUrl);
        if (shortenedUrl.isEmpty()) {
            log.error("Short URL not found: {}", shortUrl);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        // checking if the shortURL Not Active Status
        if (!shortenedUrl.get().getStatus().equals(URLShortenerStatus.ACTIVE)) {
            log.error("Short URL is not active: {}", shortUrl);
            return ResponseEntity.status(HttpStatus.GONE).body("Short URL is not active");
        }


        log.info("Redirecting to original URL: {}", shortenedUrl.get().getOriginalUrl());


        // End the timer and calculate processing time
        Instant end = Instant.now();
        log.info("URL shortening process ended at {}", end);

        long processingTime = Duration.between(start, end).toMillis();

        // Log the processing time
        log.info("URL shortening process took {} milliseconds", processingTime);

        CommonResponse response = CommonResponse.builder().timestamp(end).statusCode("URL_CREATED").apiName("/api/url/shorten").requestId(UUID.randomUUID().toString()).httpStatus(HttpStatus.CREATED.getReasonPhrase()).message("Shortened URL created successfully.").errors(Collections.emptyList()).processingTimeMs(processingTime).serviceVersion("1.0.0").path("/api/url/shorten").method("POST").data(shortenedUrl.get().getOriginalUrl()).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<?> deleteShortenedURL(String shortUrl) {
        // Log the request for deleting the shortened URL
        log.info("Received request to delete shortened URL: {}", shortUrl);
        // Start the timer to measure processing time
        Instant start = Instant.now();
        log.info("URL deletion process started at {}", start);
        // Validate the short URL format
        if (shortUrl == null || shortUrl.isEmpty() || !shortUrl.contains(URLShortenerConstant.HTTPS) || !shortUrl.contains(URLShortenerConstant.HTTP)) {
            log.error("Invalid short URL format: {}", shortUrl);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid short URL format");
        }
        // Check if the short URL exists
        Optional<ShortenedUrl> shortenedUrl = shortenedUrlRepository.findByShortCodeUrl(shortUrl);
        if (shortenedUrl.isEmpty()) {
            log.error("Short URL not found: {}", shortUrl);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        // Delete the shortened URL
        shortenedUrlRepository.delete(shortenedUrl.get());
        log.info("Shortened URL deleted successfully: {}", shortUrl);

        // End the timer and calculate processing time
        Instant end = Instant.now();
        log.info("URL deletion process ended at {}", end);
        long processingTime = Duration.between(start, end).toMillis();

        // Log the processing time
        log.info("URL deletion process took {} milliseconds", processingTime);
        CommonResponse response = CommonResponse.builder()
                .timestamp(end)
                .statusCode("URL_DELETED")
                .apiName("/api/url/delete")
                .requestId(UUID.randomUUID().toString())
                .httpStatus(HttpStatus.OK.getReasonPhrase())
                .message("Shortened URL deleted successfully.")
                .errors(Collections.emptyList())
                .processingTimeMs(processingTime)
                .serviceVersion("1.0.0")
                .path("/api/url/delete")
                .method("DELETE")
                .data(shortUrl)
                .build();
        log.info("Response for URL deletion: {}", response);
        // Return true indicating the deletion was successful
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public boolean updateShortenedURL(String shortUrl, String newLongUrl) {
        return false;
    }

    @Override
    public boolean isShortenedURLExists(String shortUrl) {
        return false;
    }

    @Override
    public boolean isLongURLExists(String longUrl) {
        return false;
    }

    @Override
    public ResponseEntity<?> getAllShortenedURLs(int page, int size) {
    log.info("Fetching all shortened URLs with pagination - Page: {}, Size: {}", page, size);
        // Validate pagination parameters
        if (page < 0 || size <= 0) {
            log.error("Invalid pagination parameters - Page: {}, Size: {}", page, size);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList("Invalid pagination parameters"));
        }
        // Fetch all shortened URLs with pagination with page and size
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ShortenedUrl> shortenedUrls = shortenedUrlRepository.findAll(pageable);
        if (shortenedUrls.isEmpty()) {
            log.info("No shortened URLs found for the given page and size.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("No shortened URLs found"));
        }
        log.info("Fetched {} shortened URLs successfully.", shortenedUrls.getTotalElements());
        // Log the total number of shortened URLs and the current page
        log.info("Total Shortened URLs: {}, Current Page: {}, Size: {}", shortenedUrls.getTotalElements(), page, size);
        // Create a response entity with the list of shortened URLs
        log.info("Returning the list of shortened URLs.");
        CommonResponse response = CommonResponse.builder()
                .timestamp(Instant.now())
                .statusCode("URL_FETCHED")
                .apiName("/api/url/fetchAll")
                .requestId(UUID.randomUUID().toString())
                .httpStatus(HttpStatus.OK.getReasonPhrase())
                .message("Shortened URLs fetched successfully.")
                .errors(Collections.emptyList())
                .processingTimeMs(0L) // Placeholder for processing time, can be calculated if needed
                .serviceVersion("1.0.0")
                .path("/api/url/fetchAll")
                .method("GET")
                .data(shortenedUrls.getContent())
                .build();
        log.info("Response for fetching all shortened URLs: {}", response);
        // Return the response entity with the list of shortened URLs
        log.info("Returning response entity with the list of shortened URLs.");
        // Return the list of shortened URLs
        return ResponseEntity.ok(response);
    }

}
