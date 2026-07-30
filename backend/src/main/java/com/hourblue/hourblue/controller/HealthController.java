package com.hourblue.hourblue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Milestone 1 sanity check - hit GET /api/health once the backend + DB are wired up.
 * Safe to delete once real endpoints (Section 5.1) exist.
 */
@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "service", "hourblue-backend");
    }
}
