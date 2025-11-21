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
     * Returns optional with {@link UserResponse} if user with this uuid exists, return empty optional otherwise.
     *
     * @param uuid uuid of the user to search for
     * @return Optional<UserResponse> if user was found, empty Optional otherwise
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException     if Remnawave returns 500
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     */
    Optional<UserResponse> getUserByUuid(@NonNull UUID uuid) throws RemnawaveException;

    /**
     * Creates new user from provided {@link CreateUserRequest}
     *
     * @param createUserRequest request to create a new user
     * @return UserResponse of the newly created user
     * @throws ValidationException       if the request fails validation
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException     if Remnawave returns 500
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     */
    UserResponse createUser(@NonNull CreateUserRequest createUserRequest) throws RemnawaveException;

    /**
     * Updates user by uuid or username defined in the {@link UpdateUserRequest}
     *
     * @param updateUserRequest request to create a new user
     * @return UserResponse of the newly created user
     * @throws ValidationException       if the request fails validation
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException     if Remnawave returns 500
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     */
    Optional<UserResponse> updateUserByUuidOrUsername(@NonNull UpdateUserRequest updateUserRequest) throws RemnawaveException;

    /**
     * Finds all users with given email
     *
     * @param email email address to search for
     * @return a list of {@link UserResponse} with the given email address or empty list if no users were found
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException     if Remnawave returns 500
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     */
    List<UserResponse> findUsersByEmail(@NonNull String email) throws RemnawaveException;

    /**
     * Deletes user with provided uuid. Does not throw error if uuid not exists.
     *
     * @param uuid uuid of the user to delete
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException     if Remnawave returns 500
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     */
    void deleteUser(@NonNull UUID uuid) throws RemnawaveException;

    /**
     * Revokes user subscription by changing shortUUID and subscription url
     *
     * @param uuid uuid of the user to revoke
     * @return Optional<UserResponse> of revoked user or empty optional if user with this uuid not found
     * @throws ValidationException       if email address is invalid
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException     if Remnawave returns 500
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     */
    Optional<UserResponse> revokeUserSubscription(@NonNull UUID uuid) throws RemnawaveException;
}
