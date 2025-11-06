package io.github.bruchdev.dto.user;

import io.github.bruchdev.dto.Happ;
import io.github.bruchdev.dto.InternalSquad;
import io.github.bruchdev.dto.Node;

import java.time.OffsetDateTime;
import java.util.List;

public record UserResponse(
        String uuid,
        String shortUuid,
        String username,
        Number usedTrafficBytes,
        Number lifetimeUsedTrafficBytes,
        String subLastUserAgent,
        String subLastOpenedAt,
        OffsetDateTime expireAt,
        OffsetDateTime onlineAt,
        OffsetDateTime subRevokedAt,
        OffsetDateTime lastTrafficResetAt,
        String trojanPassword,
        String vlessUuid,
        String ssPassword,
        String description,
        String tag,
        Number telegramId,
        String email,
        Integer hwidDeviceLimit,
        String firstConnectedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<InternalSquad> activeInternalSquads,
        String externalSquadUuid,
        String subscriptionUrl,
        Node lastConnectedNode,
        Happ happ,
        Status status,
        Number trafficLimitBytes,
        TrafficLimitStrategy trafficLimitStrategy,
        Number lastTriggeredThreshold
) {
}
