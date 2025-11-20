package io.github.bruchdev;

import io.github.bruchdev.dto.api.ApiResponse;
import io.github.bruchdev.exception.NotAuthenticatedException;
import io.github.bruchdev.exception.ValidationException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class ApiClient {
    private final OkHttpClient client;
    private final String baseUrl;
    private final String apiKey;
    private final String eGamesCookie;

    private ApiClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.apiKey = builder.apiKey;

        OkHttpClient finalClient = builder.client;

        if (finalClient == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            if (builder.eGamesCookie != null) {
                this.eGamesCookie = builder.eGamesCookie;
                okHttpBuilder.addInterceptor(chain -> {
                    Request oldRequest = chain.request();
                    Request newRequest = oldRequest.newBuilder().addHeader("Cookie", this.eGamesCookie).build();
                    return chain.proceed(newRequest);
                });
            } else {
                this.eGamesCookie = null;
            }

            if (builder.loggingEnabled) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addInterceptor(logging);
            }
            finalClient = okHttpBuilder.build();
        } else {
            this.eGamesCookie = null;
        }

        this.client = finalClient;

    }

    public static Builder builder(String baseUrl, String apiKey) {
        return new Builder(baseUrl, apiKey);
    }

    public ApiResponse get(String endpoint) throws ValidationException, NotAuthenticatedException {
        Request request = new Request.Builder()
                .get()
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .build();
        return makeRequest(request);
    }

    public ApiResponse post(String endpoint, String jsonBody) throws ValidationException, NotAuthenticatedException {
        var request = new Request.Builder()
                .post(RequestBody.create(jsonBody, null))
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .build();
        return makeRequest(request);
    }

    public ApiResponse post(String endpoint) throws ValidationException, NotAuthenticatedException {
        var request = new Request.Builder()
                .post(RequestBody.EMPTY)
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .build();
        return makeRequest(request);
    }

    public ApiResponse patch(String endpoint, String jsonBody) throws ValidationException, NotAuthenticatedException {
        var request = new Request.Builder()
                .patch(RequestBody.create(jsonBody, null))
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .build();
        return makeRequest(request);
    }

    public ApiResponse delete(String pathTemplate, Object... pathVariables) throws ValidationException, NotAuthenticatedException {
        var request = new Request.Builder()
                .delete()
                .url(String.format(baseUrl + pathTemplate, pathVariables))
                .header("Authorization", "Bearer " + apiKey)
                .build();

        return makeRequest(request);
    }

    @NotNull
    private ApiResponse makeRequest(Request request) {
        try (Response response = client.newCall(request).execute()) {
            return new ApiResponse(response.code(), response.body().string(), response.isSuccessful());
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
