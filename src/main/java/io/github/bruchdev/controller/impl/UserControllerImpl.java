package io.github.bruchdev.controller.impl;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.dto.api.JsonResponse;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.DeleteUserResponse;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.ErrorResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.UserNotFoundException;
import io.github.bruchdev.exception.ValidationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public final class UserControllerImpl implements UserController {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

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
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }

    /**
     * @param email email address to search for
     * @return List of users with the given email address or empty list if no users were found
     * @throws ValidationException    if email address is invalid
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
            case 404 -> List.of();
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }

    @Override
    public Boolean deleteUser(@NonNull UUID uuid) throws ValidationException, NotAuthorizedException, UserNotFoundException {
        var apiResponse = apiClient.delete("/users/" + uuid);
        return switch (apiResponse.statusCode()) {
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<DeleteUserResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield jsonResponse.response().isDeleted();
            }
            case 404 -> throw new UserNotFoundException(uuid.toString());
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }
}
