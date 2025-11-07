package io.github.bruchdev;

import io.github.bruchdev.controller.HwidUserDevicesController;
import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.impl.HwidUserDevicesControllerImpl;
import io.github.bruchdev.controller.impl.UserControllerImpl;
import tools.jackson.databind.ObjectMapper;

public final class Remnawave {
    private final UserController userController;
    private final HwidUserDevicesController hwidUserDevicesController;

    private Remnawave(Builder builder) {
        ApiClient.Builder apiBuilder = ApiClient.builder(builder.baseUrl, builder.apiKey);
        if (builder.eGamesCookie != null) {
            apiBuilder.eGamesCookie(builder.eGamesCookie);
        }
        var apiClient = apiBuilder.build();
        var objectMapper = new ObjectMapper();
        this.userController = new UserControllerImpl(apiClient, objectMapper);
        this.hwidUserDevicesController = new HwidUserDevicesControllerImpl(apiClient, objectMapper);
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
