import io.github.bruchdev.ApiClient;
import io.github.bruchdev.Remnawave;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.UserControllerImpl;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.dto.user.UpdateUserRequest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.helpers.ApiHelper;
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
import java.time.OffsetDateTime;
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
    void shouldIncludeSecretValue() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .code(200)
                .body(userResponseBody)
                .build());

        var baseUrl = mockServer.url("/").toString();
        var apiClient = ApiClient.builder(baseUrl, "apiKey")
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
    void shouldReturnUser_whenUuidExists() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        var excpectedUserResponse = ApiHelper.parseResponseBody(userResponseBody, UserResponse.class);
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        var baseUrl = mockServer.url("/").toString();
        var apiClient = ApiClient.builder(baseUrl, "apiKey")
                .build();

        UserController userController = new UserControllerImpl(apiClient);
        var userResponse = userController.getUserByUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        Assertions.assertTrue(userResponse.isPresent(), "User should be present");
        Assertions.assertEquals(excpectedUserResponse, userResponse.get());
    }

    @Test
    void shouldReturnEmpty_WhenUserUuidNotExists() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());

        String baseUrl = mockServer.url("/").toString();
        ApiClient apiClient = ApiClient.builder(baseUrl, "apiKey").build();

        UserController userController = new UserControllerImpl(apiClient);
        var userResponse = userController.getUserByUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        Assertions.assertTrue(userResponse.isEmpty(), "User should not be present");
    }

    @Test
    void shouldReturnUser_whenCreateUserSucceeds() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response.json"));
        var expectedUserResponse = ApiHelper.parseResponseBody(userResponseBody, UserResponse.class);
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(201)
                .build());

        String baseUrl = mockServer.url("/test").toString();
        ApiClient apiClient = ApiClient.builder(baseUrl, "apiKey").build();
        UserController userController = new UserControllerImpl(apiClient);

        var newUser = CreateUserRequest.builder()
                .username("test_user")
                .expireAt(expectedUserResponse.expireAt())
                .build();

        var createdUser = userController.createUser(newUser);

        Assertions.assertTrue(createdUser.isPresent());
        Assertions.assertEquals(newUser.username(), createdUser.get().username());
        Assertions.assertEquals(newUser.expireAt(), createdUser.get().expireAt());

        var recordedRequest = mockServer.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/test/users", recordedRequest.getUrl().encodedPath());

        assert recordedRequest.getBody() != null;
        var sentJson = recordedRequest.getBody().utf8();
        Assertions.assertTrue(sentJson.contains("test_user"));
        Assertions.assertTrue(sentJson.contains(expectedUserResponse.expireAt().toString()));
    }

    @Test
    void shouldReturnUser_whenUpdateUserSucceeds() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/update-user-response.json"));
        var expectedUserResponse = ApiHelper.parseResponseBody(userResponseBody, UserResponse.class);
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        String baseUrl = mockServer.url("/test").toString();
        var apiClient = ApiClient.builder(baseUrl, "apiKey").build();
        UserController userController = new UserControllerImpl(apiClient);

        var updateUserRequest = UpdateUserRequest.builder()
                .username("test_user")
                .expireAt(expectedUserResponse.expireAt())
                .email("new_email")
                .build();

        var updatedUser = userController.updateUserByUuidOrUsername(updateUserRequest).orElseThrow();

        Assertions.assertEquals(updateUserRequest.username(), updatedUser.username());
        Assertions.assertEquals(updateUserRequest.email(), updatedUser.email());

        var recordedRequest = mockServer.takeRequest();
        Assertions.assertEquals("PATCH", recordedRequest.getMethod());
        Assertions.assertEquals("/test/users", recordedRequest.getUrl().encodedPath());

        assert recordedRequest.getBody() != null;
        var sentJson = recordedRequest.getBody().utf8();
        Assertions.assertTrue(sentJson.contains("new_email"));
    }

}
