package userControllerTests;

import common.TestHelpers;
import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.exception.ValidationException;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.*;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

/*
 *  Remnawave API version 2.2.6
 */
public class CreateUserTest {
    private final String panelApi = "panel.remnawave.com/api";
    private final String apiKey = "apiKey";
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
    void tearDown() {
        mockServer.close();
    }

    @Test
    void shouldIncludeHeaders() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(201)
                .build());
        var newUser = CreateUserRequest.builder()
                .username("username")
                .expireAt(OffsetDateTime.now())
                .build();

        userController.createUser(newUser);
        var recordedRequest = mockServer.takeRequest();

        // Test all headers for the request
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"));
        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), recordedRequest.getHeaders().get("content-type"));
        Assertions.assertEquals("/" + panelApi + "/users", recordedRequest.getUrl().encodedPath());
    }

    @Test
    void shouldReturnUser_WhenCreateUserSucceeds() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(201)
                .build());

        var expectedUserResponse = TestHelpers.getDefaultUserResponse();
        var newUser = CreateUserRequest.builder()
                .username(expectedUserResponse.username())
                .expireAt(expectedUserResponse.expireAt())
                .build();

        Assertions.assertEquals(expectedUserResponse, userController.createUser(newUser));
    }

    @Test
    void shouldThrowValidationException_WhenCreateUserFails() throws Exception {
        var errorResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/validation-error-response.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(errorResponseBody)
                .code(400)
                .build());

        var newUser = CreateUserRequest.builder()
                .username("test")
                .expireAt(OffsetDateTime.now())
                .build();

        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(newUser));
    }
}
