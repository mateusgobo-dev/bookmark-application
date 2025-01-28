package br.com.mgobo.profileapp.web.controllers;

import br.com.mgobo.profileapp.api.services.CustomerService;
import br.com.mgobo.profileapp.web.dto.CheckUser;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(this.customerService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> saveProfile(@Valid @RequestBody CustomerDto customerDto) {
        return this.customerService.saveCustomer(customerDto);
    }

    @PostMapping("/check")
    public ResponseEntity<?> findByMailAndPassword(@Valid @RequestBody CheckUser checkUser){
        return this.customerService.findCustomerByMailAndPassword(checkUser.email(), checkUser.password());
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@Valid @RequestBody CustomerDto customerDto) {
        return this.customerService.updateCustomer(customerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        return this.customerService.deleteProfile(id);
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<?> findByMail(@PathVariable String mail) {
        return this.customerService.findByMail(mail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return this.customerService.findById(id);
    }
}
