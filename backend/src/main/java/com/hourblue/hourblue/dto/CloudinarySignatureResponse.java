package com.hourblue.hourblue.dto;

public record CloudinarySignatureResponse(
        String signature,
        long timestamp,
        String apiKey,
        String cloudName,
        String folder
) {
}
