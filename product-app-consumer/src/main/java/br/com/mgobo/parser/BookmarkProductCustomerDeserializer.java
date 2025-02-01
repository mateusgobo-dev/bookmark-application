package br.com.mgobo.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

@FunctionalInterface
public interface BookmarkProductCustomerDeserializer {
    Object deserialize(String json, Class<?> clazz);

    BookmarkProductCustomerDeserializer toObject = (json, clazz) -> {
        try{
            return new ObjectMapper().readValue(json, clazz);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex);
        }
    };
    Function<Object,String> toJson = value -> {
        try {
            return new ObjectMapper().writeValueAsString(value);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex);
        }
    };
}
