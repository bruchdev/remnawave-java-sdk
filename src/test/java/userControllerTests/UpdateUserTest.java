package userControllerTests;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import io.github.bruchdev.dto.user.UpdateUserRequest;
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

public class UpdateUserTest {
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


//    @Test
//    void shouldReturnUser_whenUpdateUserSucceeds() throws Exception {
//        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/update-user-payload.json"));
//        var expectedUserResponse = ApiHelper.parseResponseBody(userResponseBody, UserResponse.class);
//        mockServer.enqueue(new MockResponse.Builder()
//                .body(userResponseBody)
//                .code(200)
//                .build());
//
//        String baseUrl = mockServer.url("/test").toString();
//        var apiClient = ApiClient.builder(baseUrl, "apiKey").build();
//        UserController userController = new UserControllerImpl(apiClient, new ObjectMapper());
//
//        var updateUserRequest = UpdateUserRequest.builder()
//                .username("test_user")
//                .expireAt(expectedUserResponse.expireAt())
//                .email("new_email")
//                .build();
//
//        var updatedUser = userController.updateUserByUuidOrUsername(updateUserRequest).orElseThrow();
//
//        Assertions.assertEquals(updateUserRequest.username(), updatedUser.username());
//        Assertions.assertEquals(updateUserRequest.email(), updatedUser.email());
//
//        var recordedRequest = mockServer.takeRequest();
//        Assertions.assertEquals("PATCH", recordedRequest.getMethod());
//        Assertions.assertEquals("/test/users", recordedRequest.getUrl().encodedPath());
//
//        assert recordedRequest.getBody() != null;
//        var sentJson = recordedRequest.getBody().utf8();
//        Assertions.assertTrue(sentJson.contains("new_email"));
//    }
}
