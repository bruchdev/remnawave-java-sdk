package io.github.bruchdev.dto.hwid;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

/**
 * @param hwid        (required) hwid of the device
 * @param userUuid        (required) userUuid of the user
 * @param platform    platform of the device
 * @param osVersion   os version of the device
 * @param deviceModel device model
 * @param userAgent   user agent
 */
@Builder
public record CreateUserHwidDeviceRequest(
        @NonNull
        String hwid,
        @NonNull
        UUID userUuid,
        String platform,
        String osVersion,
        String deviceModel,
        String userAgent
) {
}
