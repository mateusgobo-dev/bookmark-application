package br.com.mgobo.profileapp.api.mapper;

import br.com.mgobo.profileapp.api.entities.Customer;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-27T22:03:49-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Ubuntu)"
)
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDto toDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String address = null;
        String mail = null;
        String password = null;

        id = customer.getId();
        name = customer.getName();
        address = customer.getAddress();
        mail = customer.getMail();
        password = customer.getPassword();

        CustomerDto customerDto = new CustomerDto( id, name, address, mail, password );

        return customerDto;
    }

    @Override
    public Customer toEntity(CustomerDto customerDto) {
        if ( customerDto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( customerDto.id() );
        customer.name( customerDto.name() );
        customer.address( customerDto.address() );
        customer.mail( customerDto.mail() );
        customer.password( customerDto.password() );

        return customer.build();
    }
}
