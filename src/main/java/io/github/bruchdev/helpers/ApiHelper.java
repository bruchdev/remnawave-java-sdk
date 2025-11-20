package io.github.bruchdev.helpers;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class ApiHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

//    public static ApiResponse getResponseOrFail(ApiResponse response) throws ValidationException, NotAuthorizedException {
//        return switch (response.statusCode()) {
//            case 200, 201 -> response;
//            case 400 -> throw new ValidationException(objectMapper.readValue(response.jsonBody(), ErrorResponse.class));
//            case 403 -> throw new NotAuthorizedException();
//            case 500 -> throw new IllegalStateException("Internal Server Error");
//            default -> throw new IllegalStateException("Unexpected value: " + response.statusCode());
//        };
//    }

    public static <T> T parseResponseBody(String body, Class<T> clazz) {
        JsonNode root = objectMapper.readTree(body);
        JsonNode responseNode = root.get("response");
        if (responseNode == null || responseNode.isNull()) {
            throw new IllegalArgumentException("Missing 'response' validation in API body");
        }
        return objectMapper.treeToValue(responseNode, clazz);
    }

}
