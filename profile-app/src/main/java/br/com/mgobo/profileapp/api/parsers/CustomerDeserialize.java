package br.com.mgobo.profileapp.api.parsers;

import br.com.mgobo.profileapp.api.entities.Customer;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static br.com.mgobo.profileapp.api.mapper.CustomerMapper.INSTANCE;

@FunctionalInterface
public interface CustomerDeserialize {
    String deserializeCustomerDto(CustomerDto customerDto);
    CustomerDeserialize deserializeDto = customerDto -> {
        try {
            return new ObjectMapper().writeValueAsString(customerDto);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex);
        }
    };

    Function<CustomerDto, Customer> customer = customerDto -> INSTANCE.toEntity(customerDto);
    Function<List<CustomerDto>, List<String>> json = customerCollection -> {
            List<String> values = new ArrayList<>();
            customerCollection.stream().forEach(customerDto -> values.add(deserializeDto.deserializeCustomerDto(customerDto)));
            return values;
    };
}
