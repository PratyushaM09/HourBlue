package com.hourblue.hourblue.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Backend only ever generates a signed-upload signature (Section 5.5) -
 * raw image bytes never pass through this server.
 */
@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(@Value("${hourblue.cloudinary.cloud-name}") String cloudName,
                                  @Value("${hourblue.cloudinary.api-key}") String apiKey,
                                  @Value("${hourblue.cloudinary.api-secret}") String apiSecret) {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }
}
