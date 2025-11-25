package io.github.bruchdev.dto.internalSquad;

import lombok.NonNull;

import java.util.List;

public record InternalSquadsResponse(
        @NonNull
        Integer total,

        @NonNull
        List<InternalSquad> internalSquads
) {
}
