package com.hourblue.hourblue.exception;

public class PhotoNotFoundException extends RuntimeException {
    public PhotoNotFoundException(String slug) {
        super("Photo not found: " + slug);
    }
}
