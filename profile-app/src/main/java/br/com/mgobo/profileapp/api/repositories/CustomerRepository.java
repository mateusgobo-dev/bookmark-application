package br.com.mgobo.profileapp.api.repositories;

import br.com.mgobo.profileapp.api.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByMail(String mail);
    Customer findCustomerByMailAndPassword(String email, String password);
}
