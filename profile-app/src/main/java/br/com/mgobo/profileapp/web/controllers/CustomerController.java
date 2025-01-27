package br.com.mgobo.profileapp.web.controllers;

import br.com.mgobo.profileapp.api.services.CustomerService;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getProfile() {
        return ResponseEntity.ok(this.customerService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> saveProfile(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto newCustomer = this.customerService.saveAndFlush(customerDto);
        return ResponseEntity.created(URI.create("/find/"+newCustomer.id())).body("Customer has been created %s".formatted(newCustomer));
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@Valid @RequestBody CustomerDto customerDto) {
        try{
            CustomerDto updateCustomer = this.customerService.saveAndFlush(customerDto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).header("/get","/find/"+customerDto.id().toString()).body(updateCustomer);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        try{
            this.customerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body("Customer %s has been deleted.".formatted(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<?> findByMail(@PathVariable String mail) {
        try{
            return ResponseEntity.ok(this.customerService.findCustomerByMail(mail));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(this.customerService.findById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
