package io.github.bruchdev.dto.api;

public record JsonResponse<T>(
        T response
) {
}
