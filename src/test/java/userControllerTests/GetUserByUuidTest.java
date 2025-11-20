package userControllerTests;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.helpers.ApiHelper;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/*
 *  Remnawave API version 2.2.6
 */
public class GetUserByUuidTest {
    private final String panelApi = "panel.remnawave.com/api";
    private final String apiKey = "apiKey";
    private final UUID uuid = UUID.randomUUID();
    private MockWebServer mockServer;
    private UserController userController;

    @BeforeEach
    void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
        String baseUrl = mockServer.url(panelApi).toString();
        ApiClient apiClient = ApiClient.builder(baseUrl, apiKey).build();
        userController = new UserControllerImpl(apiClient, new ObjectMapper());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockServer.close();
    }

    @Test
    void shouldIncludeHeaders() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        userController.getUserByUuid(uuid).orElseThrow();
        var recordedRequest = mockServer.takeRequest();

        // Test all request options
        Assertions.assertEquals("/" + panelApi + "/users/" + uuid, recordedRequest.getUrl().encodedPath());
        Assertions.assertEquals("GET", recordedRequest.getMethod());
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"));
        Assertions.assertNull(recordedRequest.getHeaders().get("content-type"));
    }

    @Test
    void shouldReturnUser_WhenUserWithUuidExists() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        var excpectedUserResponse = ApiHelper.parseResponseBody(userResponseBody, UserResponse.class);
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        var userResponse = userController.getUserByUuid(UUID.fromString(excpectedUserResponse.uuid().toString())).orElseThrow();
        Assertions.assertEquals(excpectedUserResponse, userResponse);
    }

    @Test
    void shouldReturnEmpty_WhenUserWithUuidNotExists() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());

        var userResponse = userController.getUserByUuid(uuid);
        Assertions.assertTrue(userResponse.isEmpty(), "User should not be present");
    }
}
