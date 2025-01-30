package br.com.mgobo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"br.com.mgobo.api.clients"})
@SpringBootApplication
public class ProductAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductAppApplication.class, args);
    }

}
