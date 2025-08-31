package com.aamirtechie.URLShortener.repository;

import com.aamirtechie.URLShortener.model.ShortenedUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortenedUrlRepository extends MongoRepository<ShortenedUrl, String> {

    boolean existsByShortCodeUrl(String shortCode);

    Optional<ShortenedUrl> findByShortCodeUrl(String url);
}
