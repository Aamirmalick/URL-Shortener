package com.aamirtechie.URLShortener.controller;

import com.aamirtechie.URLShortener.dto.request.URLShortenerRequest;
import com.aamirtechie.URLShortener.service.URLShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.aamirtechie.URLShortener.constant.URLShortenerConstant.URL_SHORTENER_BASE_URL;
@RestController
@RequestMapping(URL_SHORTENER_BASE_URL)
public class URLShortenerController {

    private final URLShortenerService urlShortenerService;

    public URLShortenerController(URLShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public CompletableFuture<?> shortenURL(@Valid @RequestBody(required = true) URLShortenerRequest urlShortenerRequest) {
        return urlShortenerService.shortenURL(urlShortenerRequest);
    }

    @GetMapping
    public ResponseEntity<?> redirectToOriginal(
            @RequestParam(value = "shortUrl", required = true) String shortUrl) {
        return urlShortenerService.redirectToOrginaURL(shortUrl);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteShortenedURL(
            @RequestParam(value = "shortUrl", required = true) String shortUrl) {
        return urlShortenerService.deleteShortenedURL(shortUrl);
    }

    @GetMapping("/fetchAll")
    // pass pagination parameters page number and size
    public ResponseEntity<?> getAllShortenedURLs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(urlShortenerService.getAllShortenedURLs(page, size));
    }

}
