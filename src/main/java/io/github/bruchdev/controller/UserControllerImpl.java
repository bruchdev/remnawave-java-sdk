package io.github.bruchdev.controller;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.dto.api.JsonResponse;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.ErrorResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.ValidationException;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
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
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<UserResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield Optional.of(jsonResponse.response());
            }
            case 404 -> Optional.empty();
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.jsonBody(), ErrorResponse.class);
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
            case 201 -> {
                var typeRef = new TypeReference<JsonResponse<UserResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield Optional.of(jsonResponse.response());
            }
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.jsonBody(), ErrorResponse.class);
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
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<UserResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield Optional.of(jsonResponse.response());
            }
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.jsonBody(), ErrorResponse.class);
                throw new ValidationException(err);
            }
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }

    /**
     * @param email email address to search for
     * @return List of users with the given email address or empty list if no users were found
     * @throws ValidationException if email address is invalid
     * @throws NotAuthorizedException if not authorized
     */
    @Override
    public List<UserResponse> findUsersByEmail(@NonNull String email) throws ValidationException, NotAuthorizedException {
        var apiResponse = apiClient.get("/users/by-email/" + email);
        return switch (apiResponse.statusCode()) {
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<List<UserResponse>>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield jsonResponse.response();
            }
            case 400 -> {
                var err = objectMapper.readValue(apiResponse.jsonBody(), ErrorResponse.class);
                throw new ValidationException(err);
            }
            case 404 -> List.of();
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }
}
