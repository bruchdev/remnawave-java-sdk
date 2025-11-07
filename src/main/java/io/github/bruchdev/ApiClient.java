package io.github.bruchdev;

import io.github.bruchdev.dto.ApiResponse;
import io.github.bruchdev.exception.NotAuthorizedException;
import io.github.bruchdev.exception.ValidationException;
import io.github.bruchdev.helpers.ApiHelper;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class ApiClient {
    private final OkHttpClient client;
    private final String baseUrl;
    private final String apiKey;
    private final String eGamesSecretKey;
    private final String eGamesSecretValue;

    private ApiClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.apiKey = builder.apiKey;

        OkHttpClient finalClient = builder.client;

        if (finalClient == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            if (builder.eGamesCookie != null) {
                String[] parts = builder.eGamesCookie.split("=");
                this.eGamesSecretKey = parts[0];
                this.eGamesSecretValue = parts[1];

                okHttpBuilder.addInterceptor(chain -> {
                    Request original = chain.request();
                    if (eGamesSecretKey != null && eGamesSecretValue != null) {
                        HttpUrl newUrl = original.url().newBuilder()
                                .addQueryParameter(eGamesSecretKey, eGamesSecretValue)
                                .build();
                        Request request = original.newBuilder().url(newUrl).build();
                        return chain.proceed(request);
                    }
                    return chain.proceed(original);
                });
            } else {
                this.eGamesSecretKey = null;
                this.eGamesSecretValue = null;
            }

            if (builder.loggingEnabled) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addInterceptor(logging);
            }
            finalClient = okHttpBuilder.build();
        } else {
            this.eGamesSecretKey = null;
            this.eGamesSecretValue = null;
        }
        this.client = finalClient;

    }

    public static Builder builder(String baseUrl, String apiKey) {
        return new Builder(baseUrl, apiKey);
    }

    public ApiResponse get(String endpoint) throws ValidationException, NotAuthorizedException {
        Request request = new Request.Builder()
                .get()
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .build();

        return makeRequest(request);
    }

    public ApiResponse post(String endpoint, String jsonBody) throws ValidationException, NotAuthorizedException {
        var body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );
        var request = new Request.Builder()
                .post(body)
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .build();

        return makeRequest(request);
    }

    public ApiResponse patch(String endpoint, String jsonBody) throws ValidationException, NotAuthorizedException {
        var body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );
        var request = new Request.Builder()
                .patch(body)
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .build();

        return makeRequest(request);
    }

    @NotNull
    private ApiResponse makeRequest(Request request) throws ValidationException, NotAuthorizedException {
        try (Response response = client.newCall(request).execute()) {
            var apiResponse = new ApiResponse(response.code(), response.body().string(), response.isSuccessful());
            ApiHelper.handleGlobalErrors(apiResponse);
            return apiResponse;
        } catch (IOException e) {
            return new ApiResponse(0, "Network error: " + e.getMessage(), false);
        }
    }

    public static class Builder {
        private final String baseUrl;
        private final String apiKey;
        private OkHttpClient client;
        private String eGamesCookie;
        private boolean loggingEnabled = false;

        private Builder(String baseUrl, String apiKey) {
            this.baseUrl = baseUrl;
            this.apiKey = apiKey;
        }

        public Builder okHttpClient(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public Builder eGamesCookie(String eGamesCookie) {
            if (eGamesCookie == null || eGamesCookie.isEmpty()) {
                throw new IllegalArgumentException("eGamesCookie must be provided");
            }
            this.eGamesCookie = eGamesCookie;
            return this;
        }

        public Builder enableLogging(boolean enable) {
            this.loggingEnabled = enable;
            return this;
        }

        public ApiClient build() {
            if (eGamesCookie != null && client != null) {
                throw new IllegalStateException("eGamesCookie and okHttpClient can not be used together");
            }

            return new ApiClient(this);
        }
    }
}
