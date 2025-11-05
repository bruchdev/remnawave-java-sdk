package io.github.bruchdev.dto;

import java.util.List;

public record UserResponse(
        String uuid,
        String shortUuid,
        String username,
        Number usedTrafficBytes,
        Number lifetimeUsedTrafficBytes,
        String subLastUserAgent,
        String subLastOpenedAt,
        String expireAt,
        String onlineAt,
        String subRevokedAt,
        String lastTrafficResetAt,
        String trojanPassword,
        String vlessUuid,
        String ssPassword,
        String description,
        String tag,
        Number telegramId,
        String email,
        Integer hwidDeviceLimit,
        String firstConnectedAt,
        String createdAt,
        String updatedAt,
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
    public enum Status {
        ACTIVE,
        DISABLED,
        LIMITED,
        EXPIRED
    }

    public enum TrafficLimitStrategy {
        NO_RESET,
        DAY,
        WEEK,
        MONTH
    }
}
