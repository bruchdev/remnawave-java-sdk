package io.github.bruchdev.dto.hwid;

import lombok.NonNull;

import java.util.UUID;

public record DeleteAllUserHwidDevicesRequest(
        @NonNull UUID userUuid
) {
}
