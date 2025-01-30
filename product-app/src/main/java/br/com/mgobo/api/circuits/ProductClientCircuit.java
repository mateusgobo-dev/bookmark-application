package br.com.mgobo.api.circuits;

import br.com.mgobo.api.clients.ProductClient;
import br.com.mgobo.web.dto.ProductDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClientCircuit {
    private final ProductClient productClient;

    @CircuitBreaker(name = "productClientCircuit", fallbackMethod = "getProductsFail")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return productClient.getProducts();
    }

    public ResponseEntity<?> getProductsFail(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error on getProducts, %s".formatted(throwable.getMessage()));
    }

    @CircuitBreaker(name = "productClientCircuit", fallbackMethod = "getProductsByIdFail")
    public ResponseEntity<ProductDto> getProductsById(Long id) {
        return productClient.getProductsById(id);
    }

    public ResponseEntity<?> getProductsByIdFail(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error on getProducts by id, %s".formatted(throwable.getMessage()));
    }
}
