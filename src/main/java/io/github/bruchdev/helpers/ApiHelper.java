package io.github.bruchdev.helpers;

import io.github.bruchdev.dto.api.ApiResponse;
import io.github.bruchdev.exception.ErrorResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.ValidationException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class ApiHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handleGlobalErrors(ApiResponse response) throws NotAuthorizedException, ValidationException {
        int status = response.statusCode();

        switch (status) {
            case 400 -> {
                var err = objectMapper.readValue(response.jsonBody(), ErrorResponse.class);
                throw new ValidationException(err);
            }
            case 403 -> throw new NotAuthorizedException();
            case 500 -> throw new IllegalStateException("Internal Server Error");
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
