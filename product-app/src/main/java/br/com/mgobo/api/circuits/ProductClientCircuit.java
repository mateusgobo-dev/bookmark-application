package br.com.mgobo.api.circuits;

import br.com.mgobo.api.clients.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductClientCircuit {
    private final ProductClient productClient;

    @CircuitBreaker(name = "productClientCircuit", fallbackMethod = "getProductsFail")
    public ResponseEntity<?> getProducts() {
        return productClient.getProducts();
    }

    public ResponseEntity<?> getProductsFail(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error on getProducts, %s".formatted(throwable.getMessage()));
    }

    @CircuitBreaker(name = "productClientCircuit", fallbackMethod = "getProductsByIdFail")
    public ResponseEntity<?> getProductsById(Long id) {
        return productClient.getProductsById(id);
    }

    public ResponseEntity<?> getProductsByIdFail(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error on getProducts by id, %s".formatted(throwable.getMessage()));
    }
}
