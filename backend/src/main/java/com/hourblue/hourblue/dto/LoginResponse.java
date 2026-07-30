package com.hourblue.hourblue.dto;

public record LoginResponse(String token, String tokenType) {
    public LoginResponse(String token) {
        this(token, "Bearer");
    }
}
