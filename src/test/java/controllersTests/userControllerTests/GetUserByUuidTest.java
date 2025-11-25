package controllersTests.userControllerTests;

import controllersTests.BaseTest;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/*
 *  Remnawave API version 2.2.6
 */
public class GetUserByUuidTest extends BaseTest {

    @Test
    void shouldIncludeHeaders() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        userController.getUserByUuid(expectedUUID).orElseThrow();
        var recordedRequest = mockServer.takeRequest();

        // Test all request options
        Assertions.assertEquals("/" + apiBaseUrl + "/users/" + expectedUUID, recordedRequest.getUrl().encodedPath());
        Assertions.assertEquals("GET", recordedRequest.getMethod());
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"));
        Assertions.assertNull(recordedRequest.getHeaders().get("content-type"));
        Assertions.assertNull(recordedRequest.getBody());
    }


    @Test
    void shouldReturnUser_WhenUserWithUuidExists() throws Exception {
        var userResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(userResponseBody)
                .code(200)
                .build());

        var expectedUserResponse = getDefaultUserResponse();
        var userResponse = userController.getUserByUuid(expectedUUID).orElseThrow();
        Assertions.assertEquals(expectedUserResponse, userResponse);
    }

    @Test
    void shouldReturnEmpty_WhenUserWithUuidNotExists() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());

        var userResponse = userController.getUserByUuid(expectedUUID);
        Assertions.assertTrue(userResponse.isEmpty(), "User should not be present");
    }
}
