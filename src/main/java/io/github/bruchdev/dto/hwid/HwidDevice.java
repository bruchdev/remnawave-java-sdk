package io.github.bruchdev.dto.hwid;

import java.time.OffsetDateTime;
import java.util.UUID;

public record HwidDevice(
        String hwid,
        UUID userUuid,
        String platform,
        String osVersion,
        String deviceModel,
        String userAgent,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
