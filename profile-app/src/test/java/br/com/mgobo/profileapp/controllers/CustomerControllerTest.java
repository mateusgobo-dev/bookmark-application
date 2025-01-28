package br.com.mgobo.profileapp.controllers;

import br.com.mgobo.profileapp.BaseIntegratedTest;
import br.com.mgobo.profileapp.api.parsers.CustomerSerialize;
import br.com.mgobo.profileapp.web.controllers.CustomerController;
import br.com.mgobo.profileapp.web.dto.CheckUser;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static br.com.mgobo.profileapp.web.dto.parsers.CheckUserSerialize.parserToJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest extends BaseIntegratedTest {

    @Autowired
    private CustomerController customerController;
    private MockMvc mockMvc = null;

    @BeforeAll
    public static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    public static void tearDown()  {
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
                .content(CustomerSerialize.parserToJson.parser( customerDto ));
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
    public void testUpdate() throws Exception {
        CustomerDto customerDto = new CustomerDto(1L, "MATEUS EDUARDO GOBO", "RUA TESTE 2", "mateusgobo@gmail.com", "1234");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CustomerSerialize.parserToJson.parser( customerDto ));
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            String responseBody = response.getContentAsString();
            System.out.println(String.format("Response body: %s", responseBody));
            assertEquals(true, Objects.nonNull(responseBody));
        }else{
            fail("Response was not UPDATED. Response: " + response);
        }
    }

    @Order(3)
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

    @Order(4)
    @Test
    public void testFindByMail() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customers/mail/mateusgobo@gmail.com");
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        if(response.getStatus() == HttpStatus.OK.value()) {
            String responseBody = response.getContentAsString();
            System.out.println(String.format("Response body: %s", responseBody));
            assertEquals(true, Objects.nonNull(responseBody));
        }else{
            fail("Response was not OK");
        }
    }

    @Order(5)
    @Test
    public void testFindById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customers/1");
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        if(response.getStatus() == HttpStatus.OK.value()) {
            String responseBody = response.getContentAsString();
            System.out.println(String.format("Response body: %s", responseBody));
            assertEquals(true, Objects.nonNull(responseBody));
        }else{
            fail("Response was not OK");
        }
    }

    @Order(6)
    @Test
    public void findByMailAndPassword() throws Exception {
        CheckUser checkUser = new CheckUser("mateusgobo@gmail.com", "1234");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/customers/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parserToJson.parser(checkUser));
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        if(response.getStatus() == HttpStatus.OK.value()) {
            String responseBody = response.getContentAsString();
            System.out.println(String.format("Response body: %s", responseBody));
            assertEquals(true, Objects.nonNull(responseBody));
        }else{
            fail("Response was not OK");
        }
    }


    @Order(7)
    @Test
    public void testDelete() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        if(response.getStatus() == HttpStatus.MOVED_PERMANENTLY.value()) {
            String responseBody = response.getContentAsString();
            System.out.println(String.format("Response body: %s", responseBody));
            assertEquals(true, Objects.nonNull(responseBody));
        }else{
            fail("Response was not DELETED. Response: " + response);
        }
    }
}
