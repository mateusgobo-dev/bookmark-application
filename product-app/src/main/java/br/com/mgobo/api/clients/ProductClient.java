package br.com.mgobo.api.clients;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "productClient",
        url = "${products.url}",
        configuration = ProductClient.ProductClientConfiguration.class
)
public interface ProductClient {

    @GetMapping(name = "GetProducs from fake api", path = "/products")
    String getProducts();

    @GetMapping(name = "GetProducs from fake api by id", path = "/products/{id}")
    String getProductsById(@RequestParam("id") Long id);

    @Configuration
    class ProductClientConfiguration {
        @Bean
        public RequestInterceptor interceptorProduct(){
            return interceptor -> {

            };
        }
    }

}
