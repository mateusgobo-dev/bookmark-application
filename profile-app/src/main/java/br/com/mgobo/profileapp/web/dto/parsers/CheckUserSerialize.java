package br.com.mgobo.profileapp.web.dto.parsers;

import br.com.mgobo.profileapp.web.dto.CheckUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@FunctionalInterface
public interface CheckUserSerialize {
    String parser(CheckUser checkUser);
    CheckUserSerialize parserToJson = checkUser -> {
        try {
            return new ObjectMapper().writeValueAsString(checkUser);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex.getMessage());
        }
    };
}
