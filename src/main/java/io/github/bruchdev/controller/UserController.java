package io.github.bruchdev.controller;

import io.github.bruchdev.dto.UserResponse;

import java.util.Optional;
import java.util.UUID;

public interface UserController {
    Optional<UserResponse> getUserByUuid(UUID uuid) throws Exception;
}
