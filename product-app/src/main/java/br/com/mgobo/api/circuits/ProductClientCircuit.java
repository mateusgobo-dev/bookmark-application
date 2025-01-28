package br.com.mgobo.api.circuits;

import br.com.mgobo.api.clients.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductClientCircuit {
    private final ProductClient productClient;

    @CircuitBreaker(name = "productClientCircuit", fallbackMethod = "getProductsFail")
    public String getProducts() {
        return productClient.getProducts();
    }

    public String getProductsFail(Throwable throwable) {
        return "Error on getProducts, %s".formatted(throwable.getMessage());
    }

    @CircuitBreaker(name = "productClientCircuit", fallbackMethod = "getProductsByIdFail")
    public String getProductsById(Long id) {
        return productClient.getProductsById(id);
    }

    public String getProductsByIdFail(Throwable throwable) {
        return "Error on getProducts by id, %s".formatted(throwable.getMessage());
    }
}
