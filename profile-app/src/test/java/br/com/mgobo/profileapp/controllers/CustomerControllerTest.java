package br.com.mgobo.profileapp.controllers;

import br.com.mgobo.profileapp.web.controllers.CustomerController;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Objects;
import java.util.UUID;

import static br.com.mgobo.profileapp.api.parsers.CustomerSerialize.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;
    private MockMvc mockMvc = null;
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @BeforeAll
    public static void beforeAll() throws Exception {
        postgreSQLContainer.withDatabaseName("profile-db")
                .withUsername("sa")
                .withPassword("123")
                .withExposedPorts(5432)
                .start();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Order(1)
    @Test
    public void testSave() throws Exception {
        CustomerDto customerDto = new CustomerDto(null, "MATEUS EDUARDO GOBO", "RUA TESTE 1", "mateusgobo@gmail.com", "123");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parserToJson.parser( customerDto ));
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        if(response.getStatus() == HttpStatus.CREATED.value()) {
            String responseBody = response.getContentAsString();
            System.out.println(String.format("Response body: %s", responseBody));
            assertEquals(true, Objects.nonNull(responseBody));
        }else{
            fail("Response was not CREATED. Response: " + response);
        }
    }

    @Order(2)
    @Test
    public void testFindAll() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customers");
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        if(response.getStatus() == HttpStatus.OK.value()) {
            String responseBody = response.getContentAsString();
            System.out.println(String.format("Response body: %s", responseBody));
            assertEquals(true, Objects.nonNull(responseBody));
        }else{
            fail("Response was not OK");
        }
    }
}
