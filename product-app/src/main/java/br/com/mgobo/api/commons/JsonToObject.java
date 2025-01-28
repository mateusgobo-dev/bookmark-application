package br.com.mgobo.api.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@FunctionalInterface
public interface JsonToObject {
    Object toObject(String json, Class<?> clazz);
    JsonToObject converterToObject = (json, clazz) -> {
        try {
            return new ObjectMapper().readValue(json, clazz);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex);
        }
    };
}
