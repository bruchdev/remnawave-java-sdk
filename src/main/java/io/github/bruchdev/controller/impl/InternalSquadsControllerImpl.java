package io.github.bruchdev.controller.impl;

import io.github.bruchdev.ApiClient;
import io.github.bruchdev.controller.InternalSquadsController;
import io.github.bruchdev.dto.internalSquad.InternalSquadsResponse;
import io.github.bruchdev.exception.NotAuthenticatedException;
import io.github.bruchdev.exception.RemnawaveException;
import io.github.bruchdev.exception.RemnawaveServerException;
import io.github.bruchdev.helpers.ResponseParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InternalSquadsControllerImpl implements InternalSquadsController {
    private final ApiClient apiClient;
    private final ResponseParser parser;

    @Override
    public InternalSquadsResponse getAllInternalSquads() throws RemnawaveException {
        var response = apiClient.get("/internal-squads");
        return switch (response.statusCode()) {
            case 200 -> parser.asSingle(response.body(), InternalSquadsResponse.class);
            case 403 -> throw new NotAuthenticatedException();
            case 500 -> {
                var errorResponse = parser.asError(response.body());
                throw new RemnawaveServerException(errorResponse.message());
            }
            default -> throw new IllegalStateException("Unexpected status code" + response.statusCode());
        };
    }
}
