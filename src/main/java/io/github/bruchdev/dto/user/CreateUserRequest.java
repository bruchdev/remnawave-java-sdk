package io.github.bruchdev.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record CreateUserRequest(
        @NonNull
        String username,
        Status status,
        String shortUuid,
        String trojanPassword,
        UUID vlessUuid,
        String ssPassword,
        Long trafficLimitBytes,
        TrafficLimitStrategy trafficLimitStrategy,
        @NonNull
        OffsetDateTime expireAt,
        OffsetDateTime createdAt,
        OffsetDateTime lastTrafficResetAt,
        String description,
        String tag,
        String telegramId,
        String email,
        Integer hwidDeviceLimit,
        List<UUID> activeInternalSquads
) {
}
