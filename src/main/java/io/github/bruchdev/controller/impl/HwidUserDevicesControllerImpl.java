package io.github.bruchdev.controller.impl;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.HwidUserDevicesController;
import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.DeleteAllUserHwidDevicesRequest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.RemnawaveException;
import io.github.bruchdev.exception.RemnawaveServerException;
import io.github.bruchdev.exception.ResourceNotFoundException;
import io.github.bruchdev.helpers.ApiHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@RequiredArgsConstructor
public class HwidUserDevicesControllerImpl implements HwidUserDevicesController {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    @Override
    public UserHwidDevicesResponse createUserHwidDevice(@NonNull CreateUserHwidDeviceRequest createUserHwidDeviceRequest) throws RemnawaveException {
        String requestBody = objectMapper.writeValueAsString(createUserHwidDeviceRequest);
        var apiResponse = apiClient.post("/hwid/devices", requestBody);

        return switch (apiResponse.statusCode()) {
            case 200 -> ApiHelper.parseResponseBody(apiResponse.jsonBody(), UserHwidDevicesResponse.class);
            case 404 -> throw new ResourceNotFoundException("User not found");
            default -> throw new RemnawaveServerException("Unexpected response", apiResponse.statusCode(), null);
        };
    }

    @Override
    public UserHwidDevicesResponse deleteUserHwidDevice(@NonNull DeleteUserHwidDeviceRequest deleteUserHwidDeviceRequest) throws RemnawaveException {
        var requestBody = objectMapper.writeValueAsString(deleteUserHwidDeviceRequest);
        var apiResponse = apiClient.post("/hwid/devices/delete", requestBody);

        return switch (apiResponse.statusCode()) {
            case 200 -> ApiHelper.parseResponseBody(apiResponse.jsonBody(), UserHwidDevicesResponse.class);
            case 404 -> throw new ResourceNotFoundException("User not found");
            default -> throw new RemnawaveServerException("Unexpected response", apiResponse.statusCode(), null);
        };
    }

    @Override
    public UserHwidDevicesResponse deleteAllUserHwidDevices(@NonNull DeleteAllUserHwidDevicesRequest deleteAllUserHwidDevicesRequest) throws RemnawaveException {
        String requestBody = objectMapper.writeValueAsString(deleteAllUserHwidDevicesRequest);
        var apiResponse = apiClient.post("/hwid/devices/delete", requestBody);

        return switch (apiResponse.statusCode()) {
            case 200 -> ApiHelper.parseResponseBody(apiResponse.jsonBody(), UserHwidDevicesResponse.class);
            case 404 -> throw new ResourceNotFoundException("User not found");
            default -> throw new RemnawaveServerException("Unexpected response", apiResponse.statusCode(), null);
        };
    }

    @Override
    public UserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws RemnawaveException {
        var apiResponse = apiClient.get("/hwid/devices/" + userUuid);

        return switch (apiResponse.statusCode()) {
            case 200 -> ApiHelper.parseResponseBody(apiResponse.jsonBody(), UserHwidDevicesResponse.class);
            case 404 -> throw new ResourceNotFoundException("User not found");
            default -> throw new RemnawaveServerException("Unexpected response", apiResponse.statusCode(), null);
        };
    }


}
