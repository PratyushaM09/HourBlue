package com.hourblue.hourblue.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hourblue.hourblue.dto.CloudinarySignatureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

/**
 * Admin-only: generates a Cloudinary signed-upload signature (Section 5.5).
 * Cloudinary credentials never reach the frontend - the browser uploads directly to
 * Cloudinary using this signature, then posts the resulting URLs to /api/admin/posts.
 */
@RestController
@RequestMapping("/api/admin/uploads")
public class CloudinaryController {

    private final Cloudinary cloudinary;
    private final String cloudName;
    private final String apiKey;

    public CloudinaryController(Cloudinary cloudinary,
                                 @Value("${hourblue.cloudinary.cloud-name}") String cloudName,
                                 @Value("${hourblue.cloudinary.api-key}") String apiKey) {
        this.cloudinary = cloudinary;
        this.cloudName = cloudName;
        this.apiKey = apiKey;
    }

    @GetMapping("/signature")
    public CloudinarySignatureResponse getSignature() {
        long timestamp = System.currentTimeMillis() / 1000L;
        String folder = "hourblue";

        Map<String, Object> paramsToSign = new TreeMap<>();
        paramsToSign.put("timestamp", timestamp);
        paramsToSign.put("folder", folder);

        String signature = cloudinary.apiSignRequest(paramsToSign, cloudinary.config.apiSecret);

        return new CloudinarySignatureResponse(signature, timestamp, apiKey, cloudName, folder);
    }
}
