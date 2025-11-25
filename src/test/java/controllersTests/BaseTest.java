package controllersTests;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.HwidUserDevicesController;
import io.github.bruchdev.controller.InternalSquadsController;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.HwidUserDevicesControllerImpl;
import io.github.bruchdev.controller.impl.InternalSquadsControllerImpl;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import io.github.bruchdev.dto.common.Happ;
import io.github.bruchdev.dto.common.Node;
import io.github.bruchdev.dto.hwid.HwidDevice;
import io.github.bruchdev.dto.internalSquad.ActiveInternalSquad;
import io.github.bruchdev.dto.internalSquad.Inbound;
import io.github.bruchdev.dto.internalSquad.InternalSquad;
import io.github.bruchdev.dto.internalSquad.InternalSquadInfo;
import io.github.bruchdev.dto.user.Status;
import io.github.bruchdev.dto.user.TrafficLimitStrategy;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.helpers.ResponseParser;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class BaseTest {

    protected final static String apiBaseUrl = "panel.remnawave.com/api";
    protected final static String apiKey = "TEST_KEY";
    protected final static UUID expectedUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    protected final static OffsetDateTime expectedOffsetDateTime = OffsetDateTime.parse("2025-11-19T11:30:32.634Z");

    protected ObjectMapper mapper = new ObjectMapper();
    protected ResponseParser parser = new ResponseParser(mapper);
    protected ApiClient apiClient;
    protected MockWebServer mockServer = new MockWebServer();


    protected UserController userController;
    protected HwidUserDevicesController hwidUserDevicesController;
    protected InternalSquadsController internalSquadsController;

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
                .activeInternalSquads(List.of(new ActiveInternalSquad(expectedUUID, "string")))
                .externalSquadUuid(expectedUUID)
                .subscriptionUrl("string")
                .lastConnectedNode(new Node(expectedOffsetDateTime, "string", "string"))
                .happ(new Happ("string"))
                .build();
    }

    public static HwidDevice getDefaultHwidDevice() {
        return new HwidDevice(
                "string",
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                null,
                null,
                null,
                null,
                OffsetDateTime.parse("2025-11-22T10:57:09.152Z"),
                OffsetDateTime.parse("2025-11-22T10:57:09.152Z")
        );
    }

    public static InternalSquad getDefaultInternalSquad() {
        return new InternalSquad(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "string",
                new InternalSquadInfo(1, 1),
                List.of(getDefaultInbound()),
                OffsetDateTime.parse("2025-11-25T10:49:20.526Z"),
                OffsetDateTime.parse("2025-11-25T10:49:20.526Z")
        );
    }

    public static Inbound getDefaultInbound() {
        return new Inbound(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "string",
                "string",
                null,
                null,
                null,
                null
        );
    }

    @BeforeEach
    void startServer() throws IOException {
        mockServer.start();
        var serverBaseUrl = mockServer.url(apiBaseUrl).toString();
        this.apiClient = ApiClient.builder()
                .baseUrl(serverBaseUrl)
                .apiKey(apiKey)
                .build();

        userController = new UserControllerImpl(apiClient, parser);
        hwidUserDevicesController = new HwidUserDevicesControllerImpl(apiClient, parser);
        internalSquadsController = new InternalSquadsControllerImpl(apiClient, parser);
    }

    @AfterEach
    void stopServer() throws IOException {
        mockServer.close();
    }
}
