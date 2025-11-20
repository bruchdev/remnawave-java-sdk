package io.github.bruchdev.controller;

import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.NotAuthenticatedException;
import io.github.bruchdev.exception.RemnawaveException;
import io.github.bruchdev.exception.ValidationException;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserController {
    /**
     * @param uuid uuid of the user to search for
     * @return Optional<UserResponse> if user was found, empty Optional otherwise
     * @throws RemnawaveException if remnawave returns any exception
     */
    Optional<UserResponse> getUserByUuid(@NonNull UUID uuid) throws RemnawaveException;

    /**
     * @param createUserRequest request to create a new user
     * @return UserResponse of the newly created user
     * @throws ValidationException    if the request fails validation
     * @throws NotAuthenticatedException if not authorized
     */
    UserResponse createUser(@NonNull CreateUserRequest createUserRequest) throws RemnawaveException;

    Optional<UserResponse> updateUserByUuidOrUsername(@NonNull UpdateUserRequest updateUserRequest) throws RemnawaveException;

    /**
     * @param email email address to search for
     * @return List of users with the given email address or empty list if no users were found
     * @throws ValidationException    if email address is invalid
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException  if remnawave returns 500
     */
    List<UserResponse> findUsersByEmail(@NonNull String email) throws RemnawaveException;

    /**
     * @param uuid uuid of the user to delete
     * @throws RemnawaveException if remnawave returns any exception
     */
    void deleteUser(@NonNull UUID uuid) throws RemnawaveException;

    Optional<UserResponse> revokeUserSubscription(@NonNull UUID uuid) throws RemnawaveException;
}
