package controllersTests.userControllerTests;

import controllersTests.BaseTest;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;


public class DeleteUserTest extends BaseTest {
    @Test
    void shouldIncludeHeaders() throws Exception {
        var responseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-deleted-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .code(200)
                .body(responseBody)
                .build());

        userController.deleteUser(expectedUUID);
        var recordedRequest = mockServer.takeRequest();

        // Test all headers for the request
        Assertions.assertEquals("/" + apiBaseUrl + "/users/" + expectedUUID, recordedRequest.getUrl().encodedPath(), "Request url should match");
        Assertions.assertEquals("DELETE", recordedRequest.getMethod(), "Request method should be POST");
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"), "Authorization header should be present");
        Assertions.assertNull(recordedRequest.getHeaders().get("content-type"), "Content-type header should not be present");
        Assertions.assertEquals(0, recordedRequest.getBodySize(), "Body should not be present");
    }

    @Test
    void shouldNotThrowErrors_WhenUserWithUuidExists() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        Assertions.assertDoesNotThrow(() -> userController.deleteUser(expectedUUID));
    }

    @Test
    void shouldNotThrowErrors_WhenUserWithUuidNotExists() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());

        Assertions.assertDoesNotThrow(() -> userController.deleteUser(expectedUUID));
    }
}
