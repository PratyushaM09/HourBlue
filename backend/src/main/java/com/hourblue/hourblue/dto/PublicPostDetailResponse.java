package com.hourblue.hourblue.dto;

import com.hourblue.hourblue.model.enums.ContentType;
import com.hourblue.hourblue.model.enums.Weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PublicPostDetailResponse(
        String slug,
        String title,
        ContentType contentType,
        String imageUrl,
        String thumbnailUrl,
        String mediumUrl,
        String caption,
        String tags,
        String place,
        LocalDate capturedDate,
        LocalTime capturedTime,
        Weather weather,
        boolean featured,
        String pinterestUrl,
        String externalUrl,
        String affiliateUrl,
        List<PublicCollectionResponse> collections,
        List<PublicMoodResponse> moods,
        List<PublicPostSummaryResponse> relatedPosts
) {
}
