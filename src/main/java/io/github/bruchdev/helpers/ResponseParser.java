package io.github.bruchdev.helpers;

import io.github.bruchdev.dto.api.ResponseObject;
import io.github.bruchdev.exception.RemnawaveErrorResponse;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

public final class ResponseParser {
    private final ObjectMapper mapper;

    public ResponseParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T asSingle(String json, Class<T> elementType) {
        return getResponseObjectWithSingle(json, elementType).payload();
    }

    private <T> ResponseObject<T> getResponseObjectWithSingle(String json, Class<T> elementType) {
        JavaType type = mapper.getTypeFactory()
                .constructParametricType(
                        ResponseObject.class,
                        elementType);
        return mapper.readValue(json, type);
    }

    public <T> List<T> asList(String json, Class<T> elementType) {
        return getResponseObjectWithList(json, elementType).payload();
    }

    private <T> ResponseObject<List<T>> getResponseObjectWithList(String json, Class<T> elementType) {
        JavaType type = mapper.getTypeFactory()
                .constructParametricType(
                        ResponseObject.class,
                        mapper.getTypeFactory().constructCollectionType(List.class, elementType));
        return mapper.readValue(json, type);
    }

    public RemnawaveErrorResponse asError(String body) {
        return mapper.readValue(body, RemnawaveErrorResponse.class);
    }

}
