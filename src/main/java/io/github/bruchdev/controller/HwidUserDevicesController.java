package io.github.bruchdev.controller;

import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.DeleteAllUserHwidDevicesRequest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.UserNotFoundException;
import io.github.bruchdev.exception.ValidationException;
import lombok.NonNull;

import java.util.UUID;

public interface HwidUserDevicesController {
    UserHwidDevicesResponse createUserHwidDevice(@NonNull CreateUserHwidDeviceRequest createUserHwidDeviceRequest) throws ValidationException, NotAuthorizedException, UserNotFoundException;

    UserHwidDevicesResponse deleteUserHwidDevice(@NonNull DeleteUserHwidDeviceRequest deleteUserHwidDeviceRequest) throws ValidationException, NotAuthorizedException, UserNotFoundException;

    UserHwidDevicesResponse deleteAllUserHwidDevices(@NonNull DeleteAllUserHwidDevicesRequest deleteAllUserHwidDevicesRequest) throws ValidationException, NotAuthorizedException, UserNotFoundException;

    UserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws ValidationException, NotAuthorizedException, UserNotFoundException;
}
