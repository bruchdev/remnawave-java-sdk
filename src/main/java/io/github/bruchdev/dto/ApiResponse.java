package io.github.bruchdev.dto;

/**
 * @param body raw JSON with a response object inside {"response": {...}}
 */
public record ApiResponse(
        int statusCode,
        String body,
        boolean success) {
}
