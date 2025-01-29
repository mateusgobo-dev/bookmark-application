package br.com.mgobo.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
