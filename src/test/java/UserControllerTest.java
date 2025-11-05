import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.UserControllerImpl;
import io.github.bruchdev.dto.UserResponse;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class UserControllerTest {
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
    void testUserService_eGamesQueryParamShouldExist() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .code(200)
                .body(userResponseBody)
                .build());

        String baseUrl = mockServer.url("/").toString();
        ApiClient apiClient = ApiClient.builder(baseUrl, "apiKey")
                .eGamesCookie("secretKey=secretValue")
                .build();

        UserController userController = new UserControllerImpl(apiClient);
        userController.getUserByUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        // Assert eGames query parameter is present
        RecordedRequest recordedRequest = mockServer.takeRequest();
        String secretValue = recordedRequest.getUrl().queryParameter("secretKey");
        Assertions.assertEquals("secretValue", secretValue, "secretValue should be secretValue");
    }

    @Test
    void testUserService_UserShouldBeFound() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        String baseUrl = mockServer.url("/").toString();
        ApiClient apiClient = ApiClient.builder(baseUrl, "apiKey")
                .build();

        UserController userController = new UserControllerImpl(apiClient);
        var userResponse = userController.getUserByUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        Assertions.assertTrue(userResponse.isPresent(), "User should be present");
        Assertions.assertEquals("11111111-1111-1111-1111-111111111111", userResponse.get().uuid());
        Assertions.assertEquals(UserResponse.TrafficLimitStrategy.MONTH, userResponse.get().trafficLimitStrategy(), "trafficLimitStrategy should be MONTH");
    }

    @Test
    void testUserService_UserShouldNotBeFound() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());

        String baseUrl = mockServer.url("/").toString();
        ApiClient apiClient = ApiClient.builder(baseUrl, "apiKey").build();

        UserController userController = new UserControllerImpl(apiClient);
        var userResponse = userController.getUserByUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        Assertions.assertTrue(userResponse.isEmpty(), "User should not be present");
    }

}
