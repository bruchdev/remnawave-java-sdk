package io.github.bruchdev.dto.hwid;

import lombok.NonNull;

import java.util.UUID;

/**
 * @param uuid (required) uuid of the user
 * @param hwid (required) hwid of the device
 */
public record DeleteUserHwidDeviceRequest(
        @NonNull UUID uuid,
        @NonNull String hwid
) {
}
