package io.github.bruchdev.dto.api;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper for responses from remnawave
 *
 * @param payload actual payload
 * @param <T>     type of payload
 */
public record ResponseObject<T>(
        @JsonProperty("response")
        T payload
) {
}
