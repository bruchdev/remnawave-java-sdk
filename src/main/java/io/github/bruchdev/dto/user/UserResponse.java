package io.github.bruchdev.dto.user;

import io.github.bruchdev.dto.common.Happ;
import io.github.bruchdev.dto.internalSquad.ActiveInternalSquad;
import io.github.bruchdev.dto.internalSquad.InternalSquad;
import io.github.bruchdev.dto.common.Node;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID uuid,
        String shortUuid,
        String username,
        Long usedTrafficBytes,
        Long lifetimeUsedTrafficBytes,
        String subLastUserAgent,
        OffsetDateTime subLastOpenedAt,
        OffsetDateTime expireAt,
        OffsetDateTime onlineAt,
        OffsetDateTime subRevokedAt,
        OffsetDateTime lastTrafficResetAt,
        String trojanPassword,
        UUID vlessUuid,
        String ssPassword,
        String description,
        String tag,
        Long telegramId,
        String email,
        Integer hwidDeviceLimit,
        OffsetDateTime firstConnectedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<ActiveInternalSquad> activeInternalSquads,
        UUID externalSquadUuid,
        String subscriptionUrl,
        Node lastConnectedNode,
        Happ happ,
        Status status,
        Long trafficLimitBytes,
        TrafficLimitStrategy trafficLimitStrategy,
        Long lastTriggeredThreshold
) {
}
