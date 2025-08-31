package com.aamirtechie.URLShortener.service;

import com.aamirtechie.URLShortener.dto.request.URLShortenerRequest;
import org.springframework.http.ResponseEntity;
import java.util.concurrent.CompletableFuture;

public interface URLShortenerService {

    /*
     * Shortens a given URL.
     * @param urlShortenerRequest the request containing the URL to be shortened.
     * @return a CompletableFuture containing the response entity with the shortened URL.
     */
    CompletableFuture<ResponseEntity<String>> shortenURL(URLShortenerRequest urlShortenerRequest);

    /**
     * Redirects to the original URL based on the shortened URL.
     * @param shortUrl the shortened URL to redirect to the original URL.
     * @return a ResponseEntity containing the redirection status or error message.
     */
    ResponseEntity<?> redirectToOrginaURL(String shortUrl);

    /**
     * Deletes a shortened URL.
     * @param shortUrl the shortened URL to be deleted.
     * @return a ResponseEntity indicating the success or failure of the deletion.
     */
    ResponseEntity<?> deleteShortenedURL(String shortUrl);

    boolean updateShortenedURL(String shortUrl, String newLongUrl);

    boolean isShortenedURLExists(String shortUrl);

    boolean isLongURLExists(String longUrl);

    /**
     * Fetches all shortened URLs.
     * @return a list of all shortened URLs.
     */
    ResponseEntity<?> getAllShortenedURLs(int page, int size);
}
