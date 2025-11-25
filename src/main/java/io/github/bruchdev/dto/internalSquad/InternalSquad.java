package io.github.bruchdev.dto.internalSquad;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record InternalSquad(
        UUID uuid,
        String name,
        InternalSquadInfo info,
        List<Inbound> inbounds,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
