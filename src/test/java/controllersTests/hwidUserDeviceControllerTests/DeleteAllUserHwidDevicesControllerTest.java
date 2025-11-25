package controllersTests.hwidUserDeviceControllerTests;

import controllersTests.BaseControllerTest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteAllUserHwidDevicesControllerTest extends BaseControllerTest {

    @Test
    void shouldIncludeHeaders() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/user-hwid-device-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());

        hwidUserDevicesController.deleteAllUserHwidDevices(expectedUUID);
        var recordedRequest = mockServer.takeRequest();

        // Test all headers for the request
        Assertions.assertEquals("/" + apiBaseUrl + "/hwid/devices/delete-all", recordedRequest.getUrl().encodedPath(), "Request url should match");
        Assertions.assertEquals("POST", recordedRequest.getMethod(), "Request method should be POST");
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"), "Authorization header should be present");
        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), recordedRequest.getHeaders().get("content-type"), "Content-Type header should be json");
    }

    @Test
    void shouldReturnAllDevices_whenDeviceIsDeleted() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/user-hwid-device-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());
        var expectedDevices = parser.asSingle(body, UserHwidDevicesResponse.class);

        var dummyRequest = new DeleteUserHwidDeviceRequest(expectedUUID, "hwid");
        var devices = hwidUserDevicesController.deleteUserHwidDevice(dummyRequest);

        Assertions.assertEquals(expectedDevices, devices, "Devices should be equal");
    }
}
