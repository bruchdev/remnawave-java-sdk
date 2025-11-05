package io.github.bruchdev.helpers;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class JsonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseResponseBody(String body, Class<T> clazz) {
        JsonNode root = objectMapper.readTree(body);
        JsonNode responseNode = root.get("response");
        if (responseNode == null || responseNode.isNull()) {
            throw new IllegalArgumentException("Missing 'response' validation in API body");
        }
        return objectMapper.treeToValue(responseNode, clazz);
    }
}
