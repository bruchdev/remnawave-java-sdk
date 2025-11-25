package controllersTests.internalSquadsControllerTests;

import controllersTests.BaseTest;
import io.github.bruchdev.dto.internalSquad.InternalSquadsResponse;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class getAllInternalSquadsTest extends BaseTest {
    @Test
    void shouldIncludeHeaders() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/internal-squads-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());

        internalSquadsController.getAllInternalSquads();
        var recordedRequest = mockServer.takeRequest();

        // Test all headers for the request
        Assertions.assertEquals("/" + apiBaseUrl + "/internal-squads", recordedRequest.getUrl().encodedPath(), "Request url should match");
        Assertions.assertEquals("GET", recordedRequest.getMethod(), "Request method should be POST");
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"), "Authorization header should be present");
        Assertions.assertNull(recordedRequest.getHeaders().get("content-type"), "Content-Type header should not be present");
    }

    @Test
    void shouldReturnInternalSquads() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/internal-squads-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());

        var expectedResponse = new InternalSquadsResponse(1,
                List.of(getDefaultInternalSquad()));

        var response = internalSquadsController.getAllInternalSquads();

        Assertions.assertEquals(expectedResponse, response, "Devices should be equal");
    }
}
