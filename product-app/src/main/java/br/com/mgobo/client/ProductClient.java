package br.com.mgobo.client;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(
        url = "${products.url}",
        name = "productClient",
        configuration = ProductClient.ProductClientConfiguration.class
)
public interface ProductClient {

    @GetMapping("/products")
    String getProducts();

    @Configuration
    class ProductClientConfiguration {
        @Bean
        public RequestInterceptor interceptorProduct(){
            return interceptor -> {
                interceptor.header("","");
            };
        }
    }

}
