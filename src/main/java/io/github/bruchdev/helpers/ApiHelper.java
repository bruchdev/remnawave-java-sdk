package io.github.bruchdev.helpers;

import io.github.bruchdev.dto.ApiResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class ApiHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handleGlobalErrors(ApiResponse response) {
        int status = response.statusCode();

        switch (status) {
            case 403 -> throw new NotAuthorizedException();
            case 500 -> throw new RuntimeException("Internal Server Error");
            default -> {
            }
        }
    }

    public static <T> T parseResponseBody(String body, Class<T> clazz) {
        JsonNode root = objectMapper.readTree(body);
        JsonNode responseNode = root.get("response");
        if (responseNode == null || responseNode.isNull()) {
            throw new IllegalArgumentException("Missing 'response' validation in API body");
        }
        return objectMapper.treeToValue(responseNode, clazz);
    }
}
