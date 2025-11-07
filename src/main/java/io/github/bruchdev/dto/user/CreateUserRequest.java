package io.github.bruchdev.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record CreateUserRequest(
        String username,
        OffsetDateTime expireAt,
        Status status,
        String shortUuid,
        String trojanPassword,
        UUID vlessUuid,
        String ssPassword,
        Number trafficLimitBytes,
        TrafficLimitStrategy trafficLimitStrategy,
        OffsetDateTime createdAt,
        OffsetDateTime lastTrafficResetAt,
        String description,
        String tag,
        Long telegramId,
        String email,
        Integer hwidDeviceLimit,
        List<UUID> activeInternalSquads,
        String uuid,
        String externalSquadUuid
) {
    public CreateUserRequest {
        if (username == null) {
            throw new IllegalArgumentException("Username is required");
        }
        if (expireAt == null) {
            throw new IllegalArgumentException("ExpireAt is required");
        }
        if (trojanPassword != null && (trojanPassword.length() < 8 || trojanPassword.length() > 32)) {
            throw new IllegalArgumentException("trojanPasswordLength must be between 8 and 32 characters");
        }
        if (ssPassword != null && (ssPassword.length() < 8 || ssPassword.length() > 32)) {
            throw new IllegalArgumentException("Password length must be between 8 and 32 characters or null.");
        }
        if (tag != null && tag.length() > 16) {
            throw new IllegalArgumentException("Tag must be less than 16 characters");
        }
        if (telegramId != null && telegramId < 0) {
            throw new IllegalArgumentException("TelegramId must be greater than 0");
        }
    }
}
