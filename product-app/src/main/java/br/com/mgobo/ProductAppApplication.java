package br.com.mgobo;

import br.com.mgobo.api.entities.Product;
import br.com.mgobo.api.repositories.ProductRepository;
import br.com.mgobo.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.List;

import static br.com.mgobo.api.parser.ProductDeserialize.deserialize;

@EnableFeignClients(basePackages = {"br.com.mgobo.api.clients"})
@SpringBootApplication
public class ProductAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductAppApplication.class, args);
    }
}
