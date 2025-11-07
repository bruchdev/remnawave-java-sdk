package io.github.bruchdev.controller;

import io.github.bruchdev.dto.hwid.GetUserHwidDevicesResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.UserNotFoundException;
import io.github.bruchdev.exception.ValidationException;
import lombok.NonNull;

import java.util.UUID;

public interface HwidUserDevicesController {
    GetUserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws ValidationException, NotAuthorizedException, UserNotFoundException;
}
