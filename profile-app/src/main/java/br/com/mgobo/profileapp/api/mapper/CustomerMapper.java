package br.com.mgobo.profileapp.api.mapper;

import br.com.mgobo.profileapp.api.entities.Customer;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}
