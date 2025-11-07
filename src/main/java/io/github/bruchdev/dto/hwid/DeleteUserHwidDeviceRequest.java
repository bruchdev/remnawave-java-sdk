package io.github.bruchdev.dto.hwid;

import lombok.NonNull;

import java.util.UUID;

/**
 * @param userUuid (required) userUuid of the user
 * @param hwid (required) hwid of the device
 */
public record DeleteUserHwidDeviceRequest(
        @NonNull UUID userUuid,
        @NonNull String hwid
) {
}
