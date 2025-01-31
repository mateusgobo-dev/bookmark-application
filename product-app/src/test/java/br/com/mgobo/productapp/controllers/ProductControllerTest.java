package br.com.mgobo.productapp.controllers;

import br.com.mgobo.api.circuits.ProductClientCircuit;
import br.com.mgobo.api.entities.Product;
import br.com.mgobo.api.parser.ProductDeserialize;
import br.com.mgobo.api.repositories.ProductRepository;
import br.com.mgobo.productapp.BaseIntegratedTest;
import br.com.mgobo.web.dto.ProductDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static br.com.mgobo.api.parser.ProductDeserialize.*;

@ComponentScan(value = "br.com.mgobo.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseIntegratedTest {

    private final Path path = Path.of(System.getProperty("user.dir"), "src", "test", "resources", "products");

    @Autowired
    private ProductClientCircuit productClientCircuit;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(path);
    }

    private void saveProduct(Product product) {
        product.setId(null);
        product = this.productRepository.saveAndFlush(product);
        LoggerFactory.getLogger(getClass()).info("Product saved: {}", product);
    }

    @Test
    public void findAll() throws Exception {
        ResponseEntity<List<ProductDto>> response = this.productClientCircuit.getProducts();
        if (response.getStatusCode().is2xxSuccessful()) {
            List<ProductDto> values = response.getBody();
            if (!values.isEmpty()) {
                File file = new File(path.toFile(), "productsDto.dat");
                if (!file.exists()) {
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                        oos.writeObject(values);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                values.forEach(System.out::println);
            }
        }
    }

    @Test
    public void deserializeProducts() throws Exception {
        File file = new File(path.toFile(), "productsDto.dat");
        try (ObjectInputStream iis = new ObjectInputStream(new FileInputStream(file))) {
            List<ProductDto> values = (List<ProductDto>) iis.readObject();
            values.forEach(System.out::println);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void deserializeProductsAndSaveOnDatabase() throws Exception {
        File file = new File(path.toFile(), "productsDto.dat");
        try (ObjectInputStream iis = new ObjectInputStream(new FileInputStream(file))) {
            List<ProductDto> values = (List<ProductDto>) iis.readObject();
            values.stream().forEach(p -> {
                saveProduct(deserialize.apply(p));
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void findById() {
        ProductDto productDto = this.productClientCircuit.getProductsById(1L).getBody();
        Product product = deserialize.apply(productDto);
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
