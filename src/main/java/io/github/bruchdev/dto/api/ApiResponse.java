package io.github.bruchdev.dto.api;

/**
 * @param jsonBody raw JSON with a payload object inside {"payload": {...}}
 *                 or error object {"error": "Validation","code": 400}
 */
public record ApiResponse(
        int statusCode,
        String jsonBody,
        boolean success) {
}
