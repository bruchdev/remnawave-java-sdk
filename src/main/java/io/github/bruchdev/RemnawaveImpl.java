package io.github.bruchdev;

import io.github.bruchdev.controller.UserController;
import io.github.bruchdev.controller.UserControllerImpl;

public final class RemnawaveImpl implements Remnawave {
    private final UserController userController;

    public RemnawaveImpl(String apiKey, String eGamesSecret, String baseUrl) {
        ApiClient apiClient = ApiClient.builder(baseUrl, apiKey)
                .eGamesCookie(eGamesSecret)
                .build();
        this.userController = new UserControllerImpl(apiClient);
    }

    public RemnawaveImpl(String apiKey, String baseUrl) {
        ApiClient apiClient = ApiClient.builder(baseUrl, apiKey)
                .build();
        this.userController = new UserControllerImpl(apiClient);
    }

    @Override
    public UserController userController() {
        return this.userController;
    }
}
