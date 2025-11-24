package io.github.bruchdev.controller;

import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.*;
import lombok.NonNull;

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
     */
    /*
    REMNAWAVEBUG: version 2.2.6:
        Если передать userUuid которого не существует возвращает 404, в доке нет такого ответа
    */
    UserHwidDevicesResponse createUserHwidDevice(@NonNull CreateUserHwidDeviceRequest createUserHwidDeviceRequest) throws RemnawaveException;

    /**
     * Deletes a hwid device for a user.
     *
     * @param deleteUserHwidDeviceRequest request body for dele
     * @return {@link UserHwidDevicesResponse} response body with List of all user hwid devices
     * @throws ValidationException       if validation in the request fails, or if the user with provided uuid not found
     * @throws NotAuthenticatedException if not authorized
     * @throws RemnawaveServerException  if Remnawave returns 500 (in api version 2.2.6 if a device with provided hwid not found)
     * @throws IllegalStateException     if something unexpected happens
     * @apiNote Although this method declares {@link RemnawaveException}, only the listed subclasses are thrown by this method.
     */
    /*
    REMNAWAVEBUG: version 2.2.6:
     post /api/hwid/devices/delete
     Если передать userUuid которого не существует возвращает 500
     Если передать hwid которого не существует возвращает 400
     Наверно в обоих случаях должен возвращаться 404, либо 404 если юзер не найден и 200 если хвид не найден?
     */
    UserHwidDevicesResponse deleteUserHwidDevice(@NonNull DeleteUserHwidDeviceRequest deleteUserHwidDeviceRequest) throws RemnawaveException;
//
    /*
     REMNAWAVEBUG: version 2.2.6:
      endpoint: post /api/hwid/devices/delete-all
      Если передать userUuid которого не существует возвращает 404, в доке нет такого ответа
      */
//    UserHwidDevicesResponse deleteAllUserHwidDevices(@NonNull DeleteAllUserHwidDevicesRequest deleteAllUserHwidDevicesRequest) throws RemnawaveException;
//
     /*
     REMNAWAVEBUG: version 2.2.6:
      Если передать userUuid которого не существует возвращает 404, в доке нет такого ответа
      */
//    UserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws RemnawaveException;
}
