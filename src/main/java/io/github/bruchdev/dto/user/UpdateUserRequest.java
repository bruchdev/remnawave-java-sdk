package io.github.bruchdev.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.bruchdev.dto.internalSquad.ActiveInternalSquad;
import io.github.bruchdev.dto.internalSquad.InternalSquad;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @param username Username of the user
 * @param uuid     UUID of the user. UUID has higher priority than username, so if both are provided, username will be ignored.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record UpdateUserRequest(
        String username,
        UUID uuid,
        Status status,
        Long trafficLimitBytes,
        TrafficLimitStrategy trafficLimitStrategy,
        OffsetDateTime expireAt,
        String description,
        String tag,
        Long telegramId,
        String email,
        Integer hwidDeviceLimit,
        List<ActiveInternalSquad> activeInternalSquads,
        UUID externalSquadUuid
) {
    public UpdateUserRequest {
        if (username == null && uuid == null) {
            throw new IllegalArgumentException("Username or UUID is required");
        }
        if (expireAt == null) {
            throw new IllegalArgumentException("ExpireAt is required");
        }
        if (tag != null && tag.length() > 16) {
            throw new IllegalArgumentException("Tag must be less than 16 characters");
        }
        if (telegramId != null && telegramId < 0) {
            throw new IllegalArgumentException("TelegramId must be greater than 0");
        }
    }
}
