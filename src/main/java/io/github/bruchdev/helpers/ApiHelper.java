package io.github.bruchdev.helpers;

import io.github.bruchdev.exception.RemnawaveErrorResponse;
import io.github.bruchdev.exception.ValidationException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class ApiHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseResponseBody(String body, Class<T> clazz) {
        JsonNode root = objectMapper.readTree(body);
        JsonNode responseNode = root.get("payload");
        if (responseNode == null || responseNode.isNull()) {
            throw new IllegalArgumentException("Missing 'payload' validation in API body");
        }
        return objectMapper.treeToValue(responseNode, clazz);
    }

    public static void handleValidationErrors(String body) throws ValidationException {
        var errorResponse = objectMapper.readValue(body, RemnawaveErrorResponse.class);
        throw new ValidationException(errorResponse.errors());
    }

}
