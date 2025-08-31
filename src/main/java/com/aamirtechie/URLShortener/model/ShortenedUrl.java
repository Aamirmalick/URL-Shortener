package com.aamirtechie.URLShortener.model;

import com.aamirtechie.URLShortener.enums.URLShortenerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "shortened_urls")
public class ShortenedUrl {

    private String id;
    @NonNull
    private String shortCodeUrl;
    @NonNull
    private String originalUrl;
    private Date createdAt;
    private Date  expiresAt;
    private String userId;
    private String customAlias;
    @NotNull(message = "Status is required.")
    private URLShortenerStatus status; // e.g., "active", "expired", "deleted"
    private Long clickCount; // Number of times the short URL has been clicked
    private String lastAccessed; // Timestamp of the last access
    private String password; // Optional password for accessing the short URL
    private String description; // Optional description for the short URL
    private String tags; // Optional tags for categorization
    private String qrCodeUrl; // URL of the QR code image for the short URL
    private String analytics; // Optional analytics data (e.g., click statistics)
    private String userAgent; // User agent of the client accessing the short URL
    private String ipAddress; // IP address of the client accessing the short URL
    private String referrer; // Referrer URL of the client accessing the short URL
    private String createdBy; // User who created the short URL
    private String updatedBy; // User who last updated the short URL
    private String updatedAt; // Timestamp of the last update
    private String notes; // Optional notes or comments about the short URL
    private String expirationReason; // Reason for expiration, if applicable
    private String customDomain; // Optional custom domain for the short URL
    private String redirectType; // Type of redirect (e.g., 301, 302
    private String trackingEnabled; // Flag to indicate if tracking is enabled
    private String createdFrom; // Source from which the URL was created (e.g., web
    private String updatedFrom; // Source from which the URL was last updated (e.g., web, API)
    private String isArchived; // Flag to indicate if the URL is archived
    private String archivedAt; // Timestamp when the URL was archived
    private String archivedBy; // User who archived the URL
    private String isFeatured; // Flag to indicate if the URL is featured
    private String featuredAt; // Timestamp when the URL was featured
    private String featuredBy; // User who featured the URL
    private String isPinned; // Flag to indicate if the URL is pinned
    private String pinnedAt; // Timestamp when the URL was pinned
    private String pinnedBy; // User who pinned the URL
    private String isPublic; // Flag to indicate if the URL is public
    private String isPrivate; // Flag to indicate if the URL is private
    private String isDeleted; // Flag to indicate if the URL is deleted
    private String deletedAt; // Timestamp when the URL was deleted
    private String deletedBy; // User who deleted the URL
    private String isArchivedByUser; // Flag to indicate if the URL is archived by the
    private String archivedByUser; // User who archived the URL by the user
    private String isRestored; // Flag to indicate if the URL is restored
    private String restoredAt; // Timestamp when the URL was restored
    private String restoredBy; // User who restored the URL
    private String isVerified; // Flag to indicate if the URL is verified
    private String verifiedAt; // Timestamp when the URL was verified
    private String verifiedBy; // User who verified the URL
    private String isFlagged; // Flag to indicate if the URL is flagged
    private String flaggedAt; // Timestamp when the URL was flagged
    private String flaggedBy; // User who flagged the URL
    private String isSpam; // Flag to indicate if the URL is marked as spam
    private String spamReason; // Reason for marking the URL as spam
    private String isMalicious; // Flag to indicate if the URL is marked as malicious
    private String maliciousReason; // Reason for marking the URL as malicious
    private String isSuspicious; // Flag to indicate if the URL is marked as suspicious
    private String suspiciousReason; // Reason for marking the URL as suspicious
    private String isBlocked; // Flag to indicate if the URL is blocked
    private String blockedAt; // Timestamp when the URL was blocked
    private String blockedBy; // User who blocked the URL
    private String isUnblocked; // Flag to indicate if the URL is unblocked
    private String unblockedAt; // Timestamp when the URL was unblocked
    private String unblockedBy; // User who unblocked the URL
    private String isArchivedByAdmin; // Flag to indicate if the URL is archived by an
    private String archivedByAdmin; // User who archived the URL by an admin
    private String isRestoredByAdmin; // Flag to indicate if the URL is restored by
    private String restoredByAdmin; // User who restored the URL by an admin
    private String isVerifiedByAdmin; // Flag to indicate if the URL is verified by an
    private String verifiedByAdmin; // User who verified the URL by an admin
    private String isFlaggedByAdmin; // Flag to indicate if the URL is flagged by
    private String flaggedByAdmin; // User who flagged the URL by an admin
    private String isSpamByAdmin; // Flag to indicate if the URL is marked as spam
    private String spamReasonByAdmin; // Reason for marking the URL as spam by an admin
    private String isMaliciousByAdmin; // Flag to indicate if the URL is marked as
}
