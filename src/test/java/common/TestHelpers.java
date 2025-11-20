package common;

import io.github.bruchdev.dto.common.Happ;
import io.github.bruchdev.dto.common.InternalSquad;
import io.github.bruchdev.dto.common.Node;
import io.github.bruchdev.dto.user.Status;
import io.github.bruchdev.dto.user.TrafficLimitStrategy;
import io.github.bruchdev.dto.user.UserResponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class TestHelpers {
    private final static UUID expectedUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    private final static OffsetDateTime expectedOffsetDateTime = OffsetDateTime.parse("2025-11-19T11:30:32.634Z");

    public static UserResponse getDefaultUserResponse() {
        return UserResponse.builder()
                .uuid(expectedUUID)
                .shortUuid("string")
                .username("string")
                .status(Status.ACTIVE)
                .usedTrafficBytes(0L)
                .lifetimeUsedTrafficBytes(0L)
                .trafficLimitBytes(0L)
                .trafficLimitStrategy(TrafficLimitStrategy.NO_RESET)
                .subLastUserAgent("string")
                .subLastOpenedAt(expectedOffsetDateTime)
                .expireAt(expectedOffsetDateTime)
                .onlineAt(expectedOffsetDateTime)
                .subRevokedAt(expectedOffsetDateTime)
                .lastTrafficResetAt(expectedOffsetDateTime)
                .trojanPassword("string")
                .vlessUuid(expectedUUID)
                .ssPassword("string")
                .description("string")
                .tag("string")
                .telegramId(0L)
                .email("user@example.com")
                .hwidDeviceLimit(0)
                .firstConnectedAt(expectedOffsetDateTime)
                .lastTriggeredThreshold(0L)
                .createdAt(expectedOffsetDateTime)
                .updatedAt(expectedOffsetDateTime)
                .activeInternalSquads(List.of(new InternalSquad(expectedUUID, "string")))
                .externalSquadUuid(expectedUUID)
                .subscriptionUrl("string")
                .lastConnectedNode(new Node(expectedOffsetDateTime, "string", "string"))
                .happ(new Happ("string"))
                .build();
    }
}
