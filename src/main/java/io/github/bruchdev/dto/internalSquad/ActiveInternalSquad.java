package io.github.bruchdev.dto.internalSquad;

import java.util.UUID;

public record ActiveInternalSquad(
        UUID uuid,
        String name
) {
}
