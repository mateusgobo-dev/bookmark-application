package br.com.mgobo.productapp.controllers;

import br.com.mgobo.api.commons.JsonToObject;
import br.com.mgobo.api.entities.Product;
import br.com.mgobo.api.entities.Rating;
import br.com.mgobo.api.parser.ProductDeserialize;
import br.com.mgobo.api.repositories.ProductRepository;
import br.com.mgobo.api.repositories.RatingRepository;
import br.com.mgobo.productapp.BaseIntegratedTest;
import br.com.mgobo.api.circuits.ProductClientCircuit;
import br.com.mgobo.web.dto.ProductDto;
import br.com.mgobo.web.parser.ProductDtoSerialize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@EnableFeignClients
@ComponentScan(value = "br.com.mgobo.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseIntegratedTest {

    @Autowired
    private ProductClientCircuit productClientCircuit;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        postgreSQLContainer.stop();
    }

    @Test
    public void findAll() {
        String value = this.productClientCircuit.getProducts();
        System.out.println(value);
    }

    @Test
    public void findById() {
        String value = this.productClientCircuit.getProductsById(1L);
        ProductDto productDto = (ProductDto) JsonToObject.converterToObject.toObject(value, ProductDto.class);
        Product product = ProductDeserialize.deserialize.apply(productDto);
        product.setId(null);
        product = this.productRepository.saveAndFlush(product);
        System.out.println(product);

        List<Product> products = this.productRepository.findAll();
        products.forEach(p -> {
            System.out.println(p);
            System.out.println(p.getRating());
        });
    }
}
