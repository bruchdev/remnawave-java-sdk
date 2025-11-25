package io.github.bruchdev.controller;

import io.github.bruchdev.dto.internalSquad.InternalSquadsResponse;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.NotAuthenticatedException;
import io.github.bruchdev.exception.RemnawaveException;

public interface InternalSquadsController {

    /**
     * Returns response object with all internal squads.
     *
     * @return {@link InternalSquadsResponse} object with all internal squads
     * @throws NotAuthenticatedException if not authorized
     * @throws IllegalStateException     if Remnawave returns 500
     * @RemnawaveBug:
     * Version 2.2.6.
     * Endpoint: get /api/internal-squads
     * Description: Возвращает 400 хотя мы вообще ничего не передаём для валидации
     */
    InternalSquadsResponse getAllInternalSquads() throws RemnawaveException;
}
