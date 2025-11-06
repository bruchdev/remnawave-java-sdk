package io.github.bruchdev.controller;

import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;

import java.util.Optional;
import java.util.UUID;

public interface UserController {
    Optional<UserResponse> getUserByUuid(UUID uuid);

    Optional<UserResponse> createUser(CreateUserRequest createUserRequest);
}
