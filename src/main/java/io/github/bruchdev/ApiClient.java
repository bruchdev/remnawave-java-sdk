package io.github.bruchdev;

import io.github.bruchdev.dto.api.ApiResponse;
import io.github.bruchdev.exception.NotAuthenticatedException;
import io.github.bruchdev.exception.ValidationException;
import lombok.Builder;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Builder
public final class ApiClient {
    @Builder.Default
    private final OkHttpClient client = new OkHttpClient();
    @NonNull
    private final String baseUrl;
    @NonNull
    private final String apiKey;
    @Nullable
    private final String eGamesCookie;
    @Builder.Default
    private final ObjectMapper mapper = new ObjectMapper();

    public ApiResponse get(String endpoint) throws ValidationException, NotAuthenticatedException {
        Request request = new Request.Builder()
                .get()
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .build();
        return makeRequest(request);
    }

    public <T> ApiResponse post(String endpoint, T requestObject) throws ValidationException, NotAuthenticatedException {
        var jsonBody = mapper.writeValueAsString(requestObject);

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

    public <T> ApiResponse patch(String endpoint, T requestObject) throws ValidationException, NotAuthenticatedException {
        var jsonBody = mapper.writeValueAsString(requestObject);

        var request = new Request.Builder()
                .patch(RequestBody.create(jsonBody, null))
                .url(baseUrl + endpoint)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .build();
        return makeRequest(request);
    }

    public ApiResponse delete(String path) throws ValidationException, NotAuthenticatedException {
        var request = new Request.Builder()
                .delete()
                .url(baseUrl + path)
                .header("Authorization", "Bearer " + apiKey)
                .build();

        return makeRequest(request);
    }

    @NotNull
    private ApiResponse makeRequest(Request request) {
        if (eGamesCookie != null) {
            request = request.newBuilder().addHeader("Cookie", eGamesCookie).build();
        }
        try (Response response = client.newCall(request).execute()) {
            return new ApiResponse(response.code(), response.body().string());
        } catch (IOException e) {
            return new ApiResponse(0, "Network error: " + e.getMessage());
        }
    }
}
