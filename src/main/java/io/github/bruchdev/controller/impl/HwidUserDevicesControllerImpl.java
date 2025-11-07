package io.github.bruchdev.controller.impl;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.HwidUserDevicesController;
import io.github.bruchdev.dto.api.JsonResponse;
import io.github.bruchdev.dto.hwid.CreateUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.DeleteUserHwidDeviceRequest;
import io.github.bruchdev.dto.hwid.UserHwidDevicesResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.UserNotFoundException;
import io.github.bruchdev.exception.ValidationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@RequiredArgsConstructor
public class HwidUserDevicesControllerImpl implements HwidUserDevicesController {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    @Override
    public UserHwidDevicesResponse createUserHwidDevice(@NonNull CreateUserHwidDeviceRequest createUserHwidDeviceRequest) throws ValidationException, NotAuthorizedException, UserNotFoundException {
        String requestBody = objectMapper.writeValueAsString(createUserHwidDeviceRequest);
        var apiResponse = apiClient.post("/hwid/devices", requestBody);
        return switch (apiResponse.statusCode()) {
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<UserHwidDevicesResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield jsonResponse.response();
            }
            case 404 -> throw new UserNotFoundException(createUserHwidDeviceRequest.uuid().toString());
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }

    @Override
    public UserHwidDevicesResponse deleteUserHwidDevice(@NonNull DeleteUserHwidDeviceRequest deleteUserHwidDeviceRequest) throws ValidationException, NotAuthorizedException, UserNotFoundException {
        String requestBody = objectMapper.writeValueAsString(deleteUserHwidDeviceRequest);
        var apiResponse = apiClient.post("/hwid/devices/delete", requestBody);
        return switch (apiResponse.statusCode()) {
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<UserHwidDevicesResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield jsonResponse.response();
            }
            case 404 -> throw new UserNotFoundException(deleteUserHwidDeviceRequest.uuid().toString());
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }

    @Override
    public UserHwidDevicesResponse getUserHwidDevices(@NonNull UUID userUuid) throws ValidationException, NotAuthorizedException, UserNotFoundException {
        var apiResponse = apiClient.get("/hwid/devices/" + userUuid);
        return switch (apiResponse.statusCode()) {
            case 200 -> {
                var typeRef = new TypeReference<JsonResponse<UserHwidDevicesResponse>>() {
                };
                var jsonResponse = objectMapper.readValue(apiResponse.jsonBody(), typeRef);
                yield jsonResponse.response();
            }
            case 404 -> throw new UserNotFoundException(userUuid.toString());
            default -> throw new IllegalStateException("Unexpected value: " + apiResponse.statusCode());
        };
    }

}
