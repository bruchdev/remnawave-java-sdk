package io.github.bruchdev.controller.impl;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.RevokeUserSubscriptionRequest;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.NotAuthenticatedException;
import io.github.bruchdev.exception.RemnawaveException;
import io.github.bruchdev.exception.RemnawaveServerException;
import io.github.bruchdev.exception.ValidationException;
import io.github.bruchdev.helpers.ResponseParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public final class UserControllerImpl implements UserController {
    private final ApiClient apiClient;
    private final ResponseParser parser;

    @Override
    public Optional<UserResponse> getUserByUuid(@NonNull UUID uuid) throws RemnawaveException {
        var apiResponse = apiClient.get("/users/" + uuid);
        return switch (apiResponse.statusCode()) {
            case 200 -> Optional.of(parser.asSingle(apiResponse.body(), UserResponse.class));
            case 404 -> Optional.empty();
            case 403 -> throw new NotAuthenticatedException();
            case 500 -> throw new RemnawaveServerException(apiResponse.statusCode(), "Internal server error");
            default -> throw new IllegalStateException("Unexpected status code" + apiResponse.statusCode());
        };
    }

    @Override
    public UserResponse createUser(@NotNull CreateUserRequest createUserRequest) throws RemnawaveException {
        var apiResponse = apiClient.post("/users", createUserRequest);

        return switch (apiResponse.statusCode()) {
            case 201 -> parser.asSingle(apiResponse.body(), UserResponse.class);
            case 400 -> {
                var errorResponse = parser.asError(apiResponse.body());
                throw new ValidationException(errorResponse.errors());
            }
            case 403 -> throw new NotAuthenticatedException();
            case 500 -> throw new RemnawaveServerException(apiResponse.statusCode(), "Internal server error");
            default -> throw new IllegalStateException("Unexpected status code" + apiResponse.statusCode());
        };
    }

    @Override
    public Optional<UserResponse> updateUserByUuidOrUsername(@NonNull UpdateUserRequest updateUserRequest) throws RemnawaveException {
        var apiResponse = apiClient.patch("/users", updateUserRequest);

        return switch (apiResponse.statusCode()) {
            case 200 -> Optional.of(parser.asSingle(apiResponse.body(), UserResponse.class));
            case 400 -> {
                var errorResponse = parser.asError(apiResponse.body());
                throw new ValidationException(errorResponse.errors());
            }
            case 403 -> throw new NotAuthenticatedException();
            case 500 -> throw new RemnawaveServerException(apiResponse.statusCode(), "Internal server error");
            default -> throw new IllegalStateException("Unexpected status code" + apiResponse.statusCode());
        };
    }

    @Override
    public List<UserResponse> findUsersByEmail(@NonNull String email) throws RemnawaveException {
        var apiResponse = apiClient.get("/users/by-email/" + email);
        return switch (apiResponse.statusCode()) {
            case 200 -> parser.asList(apiResponse.body(), UserResponse.class);
            case 400 -> {
                var errorResponse = parser.asError(apiResponse.body());
                throw new ValidationException(errorResponse.errors());
            }
            case 404 -> List.of();
            case 500 -> throw new RemnawaveServerException(apiResponse.statusCode(), "Internal server error");
            default -> throw new IllegalStateException("Unexpected status code" + apiResponse.statusCode());
        };
    }

    @Override
    public void deleteUser(@NonNull UUID uuid) throws RemnawaveException {
        var response = apiClient.delete("/users/" + uuid);
        switch (response.statusCode()) {
            case 200, 404 -> {
                // Do nothing
            }
            case 403 -> throw new NotAuthenticatedException();
            case 500 -> throw new RemnawaveServerException(response.statusCode(), "Internal server error");
            default -> throw new IllegalStateException("Unexpected status code" + response.statusCode());
        }
    }

    @Override
    public Optional<UserResponse> revokeUserSubscription(@NonNull UUID uuid, @NotNull RevokeUserSubscriptionRequest request) throws RemnawaveException {
        var response = apiClient.post("/users/" + uuid + "/actions/revoke", request);
        return switch (response.statusCode()) {
            case 200 -> Optional.of(parser.asSingle(response.body(), UserResponse.class));
            case 404 -> Optional.empty();
            case 500 -> throw new RemnawaveServerException(response.statusCode(), "Internal server error");
            default -> throw new IllegalStateException("Unexpected status code: " + response.statusCode());
        };
    }

    @Override
    public Optional<UserResponse> revokeUserSubscription(@NonNull UUID uuid) throws RemnawaveException {
        var request = new RevokeUserSubscriptionRequest("");
        return revokeUserSubscription(uuid, request);
    }
}
