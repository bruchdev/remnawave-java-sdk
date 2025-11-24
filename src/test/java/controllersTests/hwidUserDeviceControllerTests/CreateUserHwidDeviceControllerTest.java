package controllersTests.hwidUserDeviceControllerTests;

import controllersTests.BaseControllerTest;
import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.ResourceNotFoundException;
import mockwebserver3.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Remnawave API version 2.2.6
 */
public class CreateUserHwidDeviceControllerTest extends BaseControllerTest {
    @Test
    void shouldIncludeHeaders() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/user-hwid-device-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());
        var request = CreateUserHwidDeviceRequest.builder()
                .hwid("some-hwid")
                .userUuid(expectedUUID)
                .build();

        hwidUserDevicesController.createUserHwidDevice(request);
        var recordedRequest = mockServer.takeRequest();

        // Test all headers for the request
        Assertions.assertEquals("/" + apiBaseUrl + "/hwid/devices", recordedRequest.getUrl().encodedPath(), "Request url should match");
        Assertions.assertEquals("POST", recordedRequest.getMethod(), "Request method should be POST");
        Assertions.assertEquals("Bearer " + apiKey, recordedRequest.getHeaders().get("Authorization"), "Authorization header should be present");
        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), recordedRequest.getHeaders().get("content-type"), "Content-Type header should be json");
    }

    @Test
    void shouldReturnAllDevices_whenDeviceIsCreated() throws Exception {
        var body = Files.readString(Paths.get("src/test/resources/mock-responses/user-hwid-device-response-body.json"));
        mockServer.enqueue(new MockResponse.Builder()
                .body(body)
                .code(200)
                .build());
        var expectedDevices = parser.asSingle(body, UserHwidDevicesResponse.class);
        var dummyRequest = CreateUserHwidDeviceRequest.builder()
                .hwid("some-hwid")
                .userUuid(expectedUUID)
                .build();

        var devices = hwidUserDevicesController.createUserHwidDevice(dummyRequest);

        Assertions.assertEquals(expectedDevices, devices, "Devices should be equal");
    }

    @Test
    void shouldThrowNotFoundException_WhenUserNotFound() throws Exception {
        mockServer.enqueue(new MockResponse.Builder()
                .code(404)
                .build());

        var dummyRequest = CreateUserHwidDeviceRequest.builder()
                .hwid("some-hwid")
                .userUuid(expectedUUID)
                .build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> hwidUserDevicesController.createUserHwidDevice(dummyRequest));
    }

}
