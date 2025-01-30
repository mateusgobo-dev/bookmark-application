package br.com.mgobo.api.clients;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "productClient",
        url = "${products.url}",
        configuration = ProductClient.ProductClientConfiguration.class
)
public interface ProductClient {

    @GetMapping(name = "GetProducs from fake api", path = "/products")
    ResponseEntity<?> getProducts();

    @GetMapping(name = "GetProducs from fake api by id", path = "/products/{id}")
    ResponseEntity<?> getProductsById(@RequestParam("id") Long id);

    @Configuration
    class ProductClientConfiguration {
        @EventListener(classes = ApplicationReadyEvent.class)
        public void onApplicationEvent(ApplicationReadyEvent event) {
            System.out.println("Client feign has been started");
        }
    }

}
