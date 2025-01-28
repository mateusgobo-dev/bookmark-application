package br.com.mgobo.productapp.controllers;

import br.com.mgobo.client.ProductClient;
import br.com.mgobo.productapp.BaseIntegratedTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseIntegratedTest {

    @Autowired
    private ProductClient productClient;

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
        String value = this.productClient.getProducts();
        System.out.println(value);
    }
}
