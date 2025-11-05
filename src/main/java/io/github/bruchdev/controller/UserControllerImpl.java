package io.github.bruchdev.controller;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.helpers.JsonHelper;
import io.github.bruchdev.dto.UserResponse;
import io.github.bruchdev.exception.ErrorResponse;
import io.github.bruchdev.exception.ValidationException;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

public final class UserControllerImpl implements UserController {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    public UserControllerImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<UserResponse> getUserByUuid(UUID uuid) throws ValidationException {
        var apiResponse = apiClient.get("/users/" + uuid);
        return switch (apiResponse.statusCode()) {
            case 200 -> Optional.of(JsonHelper.parseResponseBody(apiResponse.body(), UserResponse.class));
            case 404 -> Optional.empty();
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.body(), ErrorResponse.class);
                throw new ValidationException(err);
            }
            case 403 -> throw new NotAuthorizedException();
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };

    }
}
