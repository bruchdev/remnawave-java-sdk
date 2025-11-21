package common;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class EGamesAccessTest {
    private MockWebServer mockServer;

    @BeforeEach
    void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockServer.close();
    }

    @Test
    void shouldIncludeSecretValue() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-payload.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .code(200)
                .body(userResponseBody)
                .build());

        var baseUrl = mockServer.url("/").toString();
        var apiClient = ApiClient.builder(baseUrl, "apiKey")
                .eGamesCookie("secretKey=secretValue")
                .build();

        UserController userController = new UserControllerImpl(apiClient, new ObjectMapper());
        userController.getUserByUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        // Assert eGames header is present
        RecordedRequest recordedRequest = mockServer.takeRequest();
        String secretToken = recordedRequest.getHeaders().get("Cookie");
        Assertions.assertEquals("secretKey=secretValue", secretToken);
    }
}
