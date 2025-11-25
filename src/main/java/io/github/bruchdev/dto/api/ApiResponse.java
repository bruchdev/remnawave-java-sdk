package io.github.bruchdev.dto.api;

/**
 * @param body raw JSON with a response object inside {"response": {...}}
 *             or error object {"error": "Validation","code": 400}
 */
public record ApiResponse(
        int statusCode,
        String body) {
}
