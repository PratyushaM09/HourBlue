package com.hourblue.hourblue.dto;

import com.hourblue.hourblue.model.enums.ContentType;

import java.time.LocalDate;
import java.util.List;

public record PublicPostSummaryResponse(
        String slug,
        String title,
        ContentType contentType,
        String thumbnailUrl,
        String mediumUrl,
        String caption,
        String place,
        LocalDate capturedDate,
        boolean featured,
        List<PublicCollectionResponse> collections,
        List<PublicMoodResponse> moods
) {
}
