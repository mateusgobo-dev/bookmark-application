package br.com.mgobo.profileapp.api.parsers;

import br.com.mgobo.profileapp.api.entities.Customer;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.com.mgobo.profileapp.api.mapper.CustomerMapper.INSTANCE;

@FunctionalInterface
public interface CustomerSerialize {
    String parser(CustomerDto customerDto);
    CustomerSerialize parserToJson = customerDto -> {
        try {
            return new ObjectMapper().writeValueAsString(customerDto);
        }catch (JsonProcessingException ex){
            throw new RuntimeException(ex.getMessage());
        }
    };

    Function<List<Customer>, List<CustomerDto>> toListCustomerDto = customerCollection -> customerCollection.stream().map(customer -> INSTANCE.toDto(customer)).collect(Collectors.toList());
    Function<Customer, CustomerDto> customerDto = customer -> INSTANCE.toDto(customer);
}
