package commonTests;

import controllersTests.BaseTest;
import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import io.github.bruchdev.helpers.ResponseParser;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

public class EGamesAccessTest extends BaseTest {
    private final MockWebServer mockServer = new MockWebServer();
    private UserController userController;
    @BeforeEach
    void startServer() throws Exception {
        mockServer.start();
        var baseUrl = mockServer.url("/").toString();
        var apiClient = ApiClient.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .eGamesCookie("secretKey=secretValue")
                .build();
         userController = new UserControllerImpl(apiClient, new ResponseParser(new ObjectMapper()));
    }

    @AfterEach
    void stopServer() throws Exception {
        mockServer.close();
    }
    @Test
    void shouldIncludeSecretValue() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(200)
                .build());
        userController.deleteUser(expectedUUID);

        // Assert eGames header is present
        RecordedRequest recordedRequest = mockServer.takeRequest();
        String secretToken = recordedRequest.getHeaders().get("Cookie");
        Assertions.assertEquals("secretKey=secretValue", secretToken);
    }
}
