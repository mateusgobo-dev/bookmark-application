package br.com.mgobo.profileapp.api.services;

import br.com.mgobo.profileapp.api.entities.Customer;
import br.com.mgobo.profileapp.api.repositories.CustomerRepository;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.mgobo.profileapp.api.parsers.CustomerDeserialize.customer;
import static br.com.mgobo.profileapp.api.parsers.CustomerSerialize.customerDto;
import static br.com.mgobo.profileapp.api.parsers.CustomerSerialize.toListCustomerDto;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDto saveAndFlush(CustomerDto entity) {
        return customerDto.apply(customerRepository.saveAndFlush(customer.apply(entity)));
    }

    public List<CustomerDto> findAll() {
        return toListCustomerDto.apply(customerRepository.findAll());
    }

    public boolean deleteById(Long id) throws Exception{
        try {
            customerRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            throw new Exception("Error has been occured at customer removing, error %s".formatted(e.getMessage()));
        }
    }

    public CustomerDto findCustomerByMail(String mail) {
        return Optional.ofNullable(customerDto.apply(customerRepository.findCustomerByMail(mail))).orElseThrow(()->new RuntimeException("Customer has not founded for mail %s".formatted(mail)));
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id)).orElseThrow(()-> new RuntimeException("Customer has not founded for id %s".formatted(id)));
    }
}
