package controllersTests.hwidUserDeviceControllerTests;

import controllersTests.BaseTest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GetUserHwidDevicesTest extends BaseTest {
    @Test
    void shouldIncludeHeaders() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/user-hwid-device-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());

        hwidUserDevicesController.getUserHwidDevices(expectedUUID);
        var recordedRequest = mockServer.takeRequest();

        // Test all headers for the request
        Assertions.assertEquals("/" + apiBaseUrl + "/hwid/devices/" + expectedUUID, recordedRequest.getUrl().encodedPath(), "Request url should match");
        Assertions.assertEquals("GET", recordedRequest.getMethod(), "Request method should be POST");
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"), "Authorization header should be present");
        Assertions.assertNull(recordedRequest.getHeaders().get("content-type"), "Content-Type header should not be present");
    }

    @Test
    void shouldReturnAllDevices_whenUserExists() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/user-hwid-device-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());
        var expectedDevices = parser.asSingle(body, UserHwidDevicesResponse.class);

        var devices = hwidUserDevicesController.getUserHwidDevices(expectedUUID);

        Assertions.assertEquals(expectedDevices, devices, "Devices should be equal");
    }
}
