package io.github.bruchdev;

import io.github.bruchdev.controller.HwidUserDevicesController;
import io.github.bruchdev.controller.InternalSquadsController;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.HwidUserDevicesControllerImpl;
import io.github.bruchdev.controller.impl.InternalSquadsControllerImpl;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import io.github.bruchdev.helpers.ResponseParser;
import tools.jackson.databind.ObjectMapper;

public final class Remnawave {
    private final UserController userController;
    private final HwidUserDevicesController hwidUserDevicesController;
    private final InternalSquadsController internalSquadsController;

    private Remnawave(Builder builder) {
        var apiClient = ApiClient.builder()
                .baseUrl(builder.baseUrl)
                .apiKey(builder.apiKey)
                .eGamesCookie(builder.eGamesCookie)
                .build();

        var objectMapper = new ObjectMapper();
        var parser = new ResponseParser(objectMapper);
        this.userController = new UserControllerImpl(apiClient, parser);
        this.hwidUserDevicesController = new HwidUserDevicesControllerImpl(apiClient, parser);
        this.internalSquadsController = new InternalSquadsControllerImpl(apiClient, parser);
    }

    public static Remnawave.Builder builder(String baseUrl, String apiKey) {
        return new Remnawave.Builder(baseUrl, apiKey);
    }

    public UserController userController() {
        return this.userController;
    }

    public HwidUserDevicesController hwidUserDevicesController() {
        return this.hwidUserDevicesController;
    }

    public InternalSquadsController internalSquadsController() {
        return this.internalSquadsController;
    }

    public static class Builder {
        private final String baseUrl;
        private final String apiKey;
        private String eGamesCookie;

        private Builder(String baseUrl, String apiKey) {
            this.baseUrl = baseUrl;
            this.apiKey = apiKey;
        }

        public Remnawave.Builder eGamesCookie(String eGamesCookie) {
            if (eGamesCookie == null || eGamesCookie.isEmpty()) {
                throw new IllegalArgumentException("eGamesCookie can not be null or empty");
            }
            this.eGamesCookie = eGamesCookie;
            return this;
        }

        public Remnawave build() {
            return new Remnawave(this);
        }
    }
}
