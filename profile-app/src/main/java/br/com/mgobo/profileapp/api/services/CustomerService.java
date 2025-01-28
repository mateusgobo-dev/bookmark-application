package br.com.mgobo.profileapp.api.services;

import br.com.mgobo.profileapp.api.entities.Customer;
import br.com.mgobo.profileapp.api.parsers.CustomerDeserialize;
import br.com.mgobo.profileapp.api.repositories.CustomerRepository;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.util.Optional;

import static br.com.mgobo.profileapp.api.parsers.CustomerDeserialize.json;
import static br.com.mgobo.profileapp.api.parsers.CustomerSerialize.customerDto;
import static br.com.mgobo.profileapp.api.parsers.CustomerSerialize.toListCustomerDto;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public ResponseEntity<?> saveCustomer(CustomerDto customer) {
        CustomerDto newCustomer = customerDto.apply(customerRepository.saveAndFlush(CustomerDeserialize.customer.apply(customer)));
        return ResponseEntity.created(URI.create("/find/" + newCustomer.id()))
                .header("/find/" + newCustomer.id(), newCustomer.name())
                .body("Customer has been created %s".formatted(newCustomer));
    }

    public ResponseEntity<?> updateCustomer(CustomerDto customer) {
        try {
            CustomerDto updateCustomer = customerDto.apply(customerRepository.saveAndFlush(CustomerDeserialize.customer.apply(customer)));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).header("/get", "/find/" + customer.id().toString()).body(updateCustomer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(toListCustomerDto.apply(customerRepository.findAll()));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    public ResponseEntity<?> deleteProfile(Long id) {
        try{
            this.customerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body("Customer %s has been deleted.".formatted(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> findByMail(String mail) {
        try{
            CustomerDto customer = Optional.ofNullable(customerDto.apply(customerRepository.findCustomerByMail(mail))).orElseThrow(() -> new RuntimeException("Customer has not founded for mail %s".formatted(mail)));
            return ResponseEntity.ok(customer);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> findById(Long id) {
        try{
            Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(id)).orElseThrow(() -> new RuntimeException("Customer has not founded for id %s".formatted(id)));
            return ResponseEntity.ok(customerDto.apply(customer.get()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> findCustomerByMailAndPassword(String email, String password) {
        try {
            CustomerDto customer = Optional.ofNullable(customerDto.apply(customerRepository.findCustomerByMailAndPassword(email, password))).orElseThrow(() -> new RuntimeException("Customer has not been founded for email or password"));
            return ResponseEntity.ok(customer);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
