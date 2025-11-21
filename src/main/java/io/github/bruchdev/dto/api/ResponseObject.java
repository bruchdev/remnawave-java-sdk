package io.github.bruchdev.dto.api;


/**
 * Wrapper for responses from remnawave
 *
 * @param payload actual payload
 * @param <T> type of payload
 */
public record ResponseObject<T>(
        T payload
) {
}
