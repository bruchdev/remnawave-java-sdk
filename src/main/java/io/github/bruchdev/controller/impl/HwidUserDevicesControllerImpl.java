package io.github.bruchdev.controller.impl;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.HwidUserDevicesController;
import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.RemnawaveException;
import io.github.bruchdev.exception.RemnawaveServerException;
import io.github.bruchdev.exception.ResourceNotFoundException;
import io.github.bruchdev.exception.ValidationException;
import io.github.bruchdev.helpers.ResponseParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HwidUserDevicesControllerImpl implements HwidUserDevicesController {
    private final ApiClient apiClient;
    private final ResponseParser parser;

    @Override
    public UserHwidDevicesResponse createUserHwidDevice(@NonNull CreateUserHwidDeviceRequest createUserHwidDeviceRequest) throws RemnawaveException {
        var response = apiClient.post("/hwid/devices", createUserHwidDeviceRequest);

        return switch (response.statusCode()) {
            case 200 -> parser.asSingle(response.body(), UserHwidDevicesResponse.class);
            case 400 -> {
                var errorResponse = parser.asError(response.body());
                throw new ValidationException(errorResponse.errors());
            }
            case 404 -> throw new ResourceNotFoundException("User not found");
            case 500 -> {
                var errorResponse = parser.asError(response.body());
                throw new RemnawaveServerException(errorResponse.statusCode(), errorResponse.message());
            }
            default -> throw new IllegalStateException("Unexpected status code: " + response.statusCode());
        };
    }

    @Override
    public UserHwidDevicesResponse deleteUserHwidDevice(@NonNull DeleteUserHwidDeviceRequest deleteUserHwidDeviceRequest) throws RemnawaveException {
        var response = apiClient.post("/hwid/devices/delete", deleteUserHwidDeviceRequest);

        return switch (response.statusCode()) {
            case 200 -> parser.asSingle(response.body(), UserHwidDevicesResponse.class);
            case 400 -> {
                var errorResponse = parser.asError(response.body());
                throw new ValidationException(errorResponse.errors());
            }
            case 500 -> {
                var errorResponse = parser.asError(response.body());
                throw new RemnawaveServerException(errorResponse.statusCode(), errorResponse.message());
            }
            default -> throw new IllegalStateException("Unexpected status code: " + response.statusCode());
        };
    }
//
//    @Override
//    public UserHwidDevicesResponse deleteAllUserHwidDevices(@NonNull DeleteAllUserHwidDevicesRequest deleteAllUserHwidDevicesRequest) throws RemnawaveException {
//        String requestBody = objectMapper.writeValueAsString(deleteAllUserHwidDevicesRequest);
//        var apiResponse = apiClient.post("/hwid/devices/delete", requestBody);
//
//        return switch (apiResponse.statusCode()) {
//            case 200 -> ResponseParser.parseResponseBody(apiResponse.jsonBody(), UserHwidDevicesResponse.class);
//            case 404 -> throw new ResourceNotFoundException("User not found");
//            default -> throw new RemnawaveServerException("Unexpected payload", apiResponse.statusCode(), null);
//        };
//    }
//
//    @Override
//    public UserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws RemnawaveException {
//        var apiResponse = apiClient.get("/hwid/devices/" + userUuid);
//
//        return switch (apiResponse.statusCode()) {
//            case 200 -> ResponseParser.parseResponseBody(apiResponse.jsonBody(), UserHwidDevicesResponse.class);
//            case 404 -> throw new ResourceNotFoundException("User not found");
//            default -> throw new RemnawaveServerException("Unexpected payload", apiResponse.statusCode(), null);
//        };
//    }


}
