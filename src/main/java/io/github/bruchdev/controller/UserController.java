package io.github.bruchdev.controller;

import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.ValidationException;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserController {
    Optional<UserResponse> getUserByUuid(@NonNull UUID uuid) throws ValidationException, NotAuthorizedException;

    Optional<UserResponse> createUser(@NonNull CreateUserRequest createUserRequest) throws ValidationException, NotAuthorizedException;

    Optional<UserResponse> updateUserByUuidOrUsername(@NonNull UpdateUserRequest updateUserRequest) throws ValidationException, NotAuthorizedException;

    List<UserResponse> findUsersByEmail(@NonNull String email) throws ValidationException, NotAuthorizedException;
}
