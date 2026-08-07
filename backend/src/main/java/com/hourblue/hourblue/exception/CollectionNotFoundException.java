package com.hourblue.hourblue.exception;

public class CollectionNotFoundException extends RuntimeException {
    public CollectionNotFoundException(String slug) {
        super("Collection not found: " + slug);
    }
}