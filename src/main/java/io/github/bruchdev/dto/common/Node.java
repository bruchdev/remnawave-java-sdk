package io.github.bruchdev.dto.common;

import java.time.OffsetDateTime;

public record Node(
        OffsetDateTime connectedAt,
        String nodeName,
        String countryCode
) {
}
