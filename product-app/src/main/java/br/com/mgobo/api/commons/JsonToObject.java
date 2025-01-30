package br.com.mgobo.api.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.function.BiFunction;

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

    BiFunction<String, Class<?>, List<Object>> converterToListObject = (json, clazz) -> {
        try {
            return (List<Object>)new ObjectMapper().readValue(json, clazz);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex);
        }
    };
}
