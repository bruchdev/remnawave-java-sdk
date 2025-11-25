package io.github.bruchdev.controller;

import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.*;
import lombok.NonNull;

import java.util.UUID;

public interface HwidUserDevicesController {

    /**
     * Creates a new hwid device for a user.
     *
     * @param createUserHwidDeviceRequest request body for creating a new user hwid device
     * @return {@link UserHwidDevicesResponse} response body with List of all user hwid devices
     * @throws ValidationException       if validation in the request fails, or if a device with this hwid already exists
     * @throws ResourceNotFoundException if a user with this uuid not found
     * @throws NotAuthenticatedException if not authorized
     * @throws RemnawaveServerException  if Remnawave returns 500
     * @throws IllegalStateException     if something unexpected happens
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     * @RemnawaveBug:
     * Version 2.2.6.
     * Endpoint: post /api/hwid/devices
     * Description: Если передать userUuid которого не существует возвращает 404, в доке этого нет
     */
    UserHwidDevicesResponse createUserHwidDevice(@NonNull CreateUserHwidDeviceRequest createUserHwidDeviceRequest) throws RemnawaveException;

    /**
     * Deletes a hwid device for a user.
     *
     * @param deleteUserHwidDeviceRequest request body for delete
     * @return {@link UserHwidDevicesResponse} response body with List of all user hwid devices
     * @throws ValidationException       if validation in the request fails, or if the user with provided uuid not found
     * @throws NotAuthenticatedException if not authorized
     * @throws RemnawaveServerException  if Remnawave returns 500
     * @throws IllegalStateException     if something unexpected happens
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     * @remnawaveBug: version 2.2.6:
     * post /api/hwid/devices/delete
     * Если передать userUuid которого не существует возвращает 404, в доке этого нет
     * Если передать hwid которого не существует возвращает 500, в доке этого нет
     */
    UserHwidDevicesResponse deleteUserHwidDevice(@NonNull DeleteUserHwidDeviceRequest deleteUserHwidDeviceRequest) throws RemnawaveException;

    /**
     * Deletes all hwid devices for a user.
     *
     * @param userUuid uuid of the user to delete all hwid devices for
     * @return {@link UserHwidDevicesResponse} response body with List of all user hwid devices
     * @throws ValidationException       if validation in the request fails, or if the user with provided uuid not found
     * @throws NotAuthenticatedException if not authorized
     * @throws RemnawaveServerException  if Remnawave returns 500
     * @throws IllegalStateException     if something unexpected happens
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     * @RemnawaveBug:
     * Version 2.2.6.
     * Endpoint: post /api/hwid/devices/delete-all.
     * Description: Если передать userUuid которого не существует возвращает 404, в доке этого нет
     */
    UserHwidDevicesResponse deleteAllUserHwidDevices(@NonNull UUID userUuid) throws RemnawaveException;

    /**
     * Return all hwid devices for a user.
     *
     * @param userUuid uuid of the user to get all hwid devices for
     * @return {@link UserHwidDevicesResponse} response body with List of all user hwid devices
     * @throws ValidationException       if validation in the request fails, or if the user with provided uuid not found
     * @throws NotAuthenticatedException if not authorized
     * @throws RemnawaveServerException  if Remnawave returns 500
     * @throws IllegalStateException     if something unexpected happens
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     * @RemnawaveBug:
     * Version 2.2.6.
     * Endpoint: post /api/hwid/devices/{userUuid}
     * Description: Если передать userUuid которого не существует возвращает 404, в доке этого нет
     */
    UserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws RemnawaveException;
}
