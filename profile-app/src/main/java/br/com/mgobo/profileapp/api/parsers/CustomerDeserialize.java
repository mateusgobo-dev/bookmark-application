package br.com.mgobo.profileapp.api.parsers;

import br.com.mgobo.profileapp.api.entities.Customer;
import br.com.mgobo.profileapp.web.dto.CustomerDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.com.mgobo.profileapp.api.mapper.CustomerMapper.INSTANCE;

public interface CustomerDeserialize {
    Function<CustomerDto, Customer> customer = customerDto -> INSTANCE.toEntity(customerDto);
}
