package controllersTests.userControllerTests;

import controllersTests.BaseTest;
import io.github.bruchdev.dto.user.CreateUserRequest;
import io.github.bruchdev.exception.ValidationException;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/*
 *  Remnawave API version 2.2.6
 */
public class CreateUserTest extends BaseTest {


    @Test
    void shouldIncludeHeaders() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(201)
                .build());
        var newUser = CreateUserRequest.builder()
                .username("username") // a username is required
                .expireAt(expectedOffsetDateTime) // expireAt is required
                .build();

        userController.createUser(newUser);
        var recordedRequest = mockServer.takeRequest();

        // Test all headers for the request
        Assertions.assertEquals("/" + apiBaseUrl + "/users", recordedRequest.getUrl().encodedPath(), "Request url should match");
        Assertions.assertEquals("POST", recordedRequest.getMethod(), "Request method should be POST");
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"), "Authorization header should be present");
        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), recordedRequest.getHeaders().get("content-type"), "Content-Type header should be json");
    }

    @Test
    void shouldReturnUser_WhenCreateUserSucceeds() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(201)
                .build());

        var expectedUserResponse = getDefaultUserResponse();
        var newUser = CreateUserRequest.builder()
                .username(expectedUserResponse.username()) // a username is required
                .expireAt(expectedOffsetDateTime) // a expireAt is required
                .build();

        Assertions.assertEquals(expectedUserResponse, userController.createUser(newUser), "Returned user should match expected");
    }

    @Test
    void shouldThrowValidationException_WhenCreateUserFails() throws Exception {
        var errorResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/validation-error-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(errorResponseBody)
                .code(400)
                .build());

        var newUser = CreateUserRequest.builder()
                .username("test") // a username is required
                .expireAt(expectedOffsetDateTime) // expireAt is required
                .build();

        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(newUser));
    }
}
