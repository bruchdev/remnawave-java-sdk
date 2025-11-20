package io.github.bruchdev.controller;

import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.DeleteAllUserHwidDevicesRequest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.RemnawaveException;
import lombok.NonNull;

import java.util.UUID;

public interface HwidUserDevicesController {
    UserHwidDevicesResponse createUserHwidDevice(@NonNull CreateUserHwidDeviceRequest createUserHwidDeviceRequest) throws RemnawaveException;

    UserHwidDevicesResponse deleteUserHwidDevice(@NonNull DeleteUserHwidDeviceRequest deleteUserHwidDeviceRequest) throws RemnawaveException;

    UserHwidDevicesResponse deleteAllUserHwidDevices(@NonNull DeleteAllUserHwidDevicesRequest deleteAllUserHwidDevicesRequest) throws RemnawaveException;

    UserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws RemnawaveException;
}
