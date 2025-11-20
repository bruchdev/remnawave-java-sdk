package io.github.bruchdev.controller.impl;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.dto.api.JsonResponse;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.*;
import io.github.bruchdev.helpers.ApiHelper;
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
    public Optional<UserResponse> getUserByUuid(@NonNull UUID uuid) throws RemnawaveException {
        var apiResponse = apiClient.get("/users/" + uuid);
        return switch (apiResponse.statusCode()) {
            case 200 -> Optional.of(ApiHelper.parseResponseBody(apiResponse.jsonBody(), UserResponse.class));
            case 404 -> Optional.empty();
            case 403 -> throw new NotAuthenticatedException();
            default -> throw new RemnawaveServerException("Internal server error", apiResponse.statusCode(), null);
        };
    }

    @Override
    public UserResponse createUser(@NotNull CreateUserRequest createUserRequest) throws RemnawaveException {
        String body = objectMapper.writeValueAsString(createUserRequest);
        var apiResponse = apiClient.post("/users", body);
        return switch (apiResponse.statusCode()) {
            case 201 -> ApiHelper.parseResponseBody(apiResponse.jsonBody(), UserResponse.class);
            case 400 -> {
                var errorResponse = objectMapper.readValue(apiResponse.jsonBody(), RemnawaveErrorResponse.class);
                throw new ValidationException(errorResponse.errors());
            }
            case 403 -> throw new NotAuthenticatedException();
            default -> throw new RemnawaveServerException("Internal server error", apiResponse.statusCode(), null);
        };
    }

    @Override
    public Optional<UserResponse> updateUserByUuidOrUsername(@NonNull UpdateUserRequest updateUserRequest) throws RemnawaveException {
        String body = objectMapper.writeValueAsString(updateUserRequest);
        var apiResponse = apiClient.patch("/users", body);
        return switch (apiResponse.statusCode()) {
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<UserResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield Optional.of(jsonResponse.response());
            }
            default -> throw new RemnawaveServerException("Internal server error", apiResponse.statusCode(), null);
        };
    }

    @Override
    public List<UserResponse> findUsersByEmail(@NonNull String email) throws RemnawaveException {
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
    public void deleteUser(@NonNull UUID uuid) throws RemnawaveException {
        var apiResponse = apiClient.delete("/users/{uuid}", uuid);
        switch (apiResponse.statusCode()) {
            case 200, 404 -> {
                // Do nothing
            }
            case 403 -> throw new NotAuthenticatedException();
            default -> throw new RemnawaveServerException("Internal server error", apiResponse.statusCode(), null);
        }
    }

    @Override
    public Optional<UserResponse> revokeUserSubscription(@NonNull UUID uuid) throws RemnawaveException {
        var apiResponse = apiClient.post("/users/" + uuid + "/actions/revoke");
        return switch (apiResponse.statusCode()) {
            case 201 -> Optional.of(ApiHelper.parseResponseBody(apiResponse.jsonBody(), UserResponse.class));
            case 404 -> Optional.empty();
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }
}
