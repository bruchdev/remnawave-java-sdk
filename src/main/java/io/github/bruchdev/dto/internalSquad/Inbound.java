package io.github.bruchdev.dto.internalSquad;

import java.util.UUID;

public record Inbound(
        UUID uuid,
        UUID profileUuid,
        String tag,
        String type,
        String network,
        String security,
        Integer port,
        String rawInbound
) {
}
