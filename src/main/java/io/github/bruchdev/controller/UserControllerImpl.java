package io.github.bruchdev.controller;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.ErrorResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.ValidationException;
import io.github.bruchdev.helpers.ApiHelper;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
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
    public Optional<UserResponse> getUserByUuid(@NonNull UUID uuid) throws ValidationException, NotAuthorizedException {
        var apiResponse = apiClient.get("/users/" + uuid);
        return switch (apiResponse.statusCode()) {
            case 200 -> Optional.of(ApiHelper.parseResponseBody(apiResponse.body(), UserResponse.class));
            case 404 -> Optional.empty();
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.body(), ErrorResponse.class);
                throw new ValidationException(err);
            }
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };

    }

    @Override
    public Optional<UserResponse> createUser(@NotNull CreateUserRequest createUserRequest) throws ValidationException, NotAuthorizedException {
        String body = objectMapper.writeValueAsString(createUserRequest);
        var apiResponse = apiClient.post("/users", body);
        return switch (apiResponse.statusCode()) {
            case 201 -> Optional.of(ApiHelper.parseResponseBody(apiResponse.body(), UserResponse.class));
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.body(), ErrorResponse.class);
                throw new ValidationException(err);
            }
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }

    @Override
    public Optional<UserResponse> updateUserByUuidOrUsername(@NonNull UpdateUserRequest updateUserRequest) throws ValidationException, NotAuthorizedException {
        String body = objectMapper.writeValueAsString(updateUserRequest);
        var apiResponse = apiClient.patch("/users", body);
        return switch (apiResponse.statusCode()) {
            case 200 -> Optional.of(ApiHelper.parseResponseBody(apiResponse.body(), UserResponse.class));
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.body(), ErrorResponse.class);
                throw new ValidationException(err);
            }
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }
}
