package controllersTests.userControllerTests;

import controllersTests.BaseTest;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*
 *  Remnawave API version 2.2.6
 */
public class FindUsersByEmailTest extends BaseTest {
    @Test
    void requestShouldIncludeHeaders() throws Exception {
        var responseBody = Files.readString(Paths.get("src/test/resources/mock-responses/2-users-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(responseBody)
                .code(200)
                .build());

        userController.findUsersByEmail("test@email.com");
        var recordedRequest = mockServer.takeRequest();

        // Test all request options
        Assertions.assertEquals("/" + apiBaseUrl + "/users/by-email/test@email.com", recordedRequest.getUrl().encodedPath());
        Assertions.assertEquals("GET", recordedRequest.getMethod());
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"));
        Assertions.assertNull(recordedRequest.getHeaders().get("content-type"));
        Assertions.assertNull(recordedRequest.getBody());
    }

    @Test
    void shouldReturn2Users_WhenUsersWithEmailExist() throws Exception {
        var responseBody = Files.readString(Paths.get("src/test/resources/mock-responses/2-users-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(responseBody)
                .code(200)
                .build());
        var expectedUsers = List.of(BaseTest.getDefaultUserResponse(), BaseTest.getDefaultUserResponse());
        var usersResponse = userController.findUsersByEmail("test"); // an email is required, ignored
        Assertions.assertEquals(expectedUsers, usersResponse);
    }

    @Test
    void shouldReturnEmptyList_WhenUsersWithEmailNotExist() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());
        var usersResponse = userController.findUsersByEmail("test"); // an email is required, ignored
        Assertions.assertTrue(usersResponse.isEmpty(), "List should be empty");
    }
}
