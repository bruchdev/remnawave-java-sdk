package controllersTests.userControllerTests;

import controllersTests.BaseTest;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RevokeUserSubscriptionTest extends BaseTest {
    @Test
    void requestShouldIncludeHeaders() throws Exception {
        var responseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(responseBody)
                .code(200)
                .build());

        userController.revokeUserSubscription(expectedUUID);
        var recordedRequest = mockServer.takeRequest();

        // Test all request options
        Assertions.assertEquals("/" + apiBaseUrl + "/users/" + expectedUUID + "/actions/revoke", recordedRequest.getUrl().encodedPath());
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"));
        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), recordedRequest.getHeaders().get("content-type"), "Content-Type header should be json");
    }

    @Test
    void shouldReturnUserResponse_whenUserWithUuidExists() throws Exception {
        var responseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(responseBody)
                .code(200)
                .build());

        var revokedUser = userController.revokeUserSubscription(expectedUUID).orElseThrow();
        Assertions.assertEquals(getDefaultUserResponse(), revokedUser, "Returned user should match expected");
    }

    @Test
    void shouldReturnEmpty_whenUserWithUuidNotExists() throws Exception {
        var responseBody = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());

        var revokedUser = userController.revokeUserSubscription(expectedUUID);
        Assertions.assertTrue(revokedUser.isEmpty(), "Returned user should match expected");
    }
}
