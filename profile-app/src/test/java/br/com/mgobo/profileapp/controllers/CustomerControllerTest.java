package br.com.mgobo.profileapp.controllers;

import br.com.mgobo.profileapp.web.controllers.CustomerController;
import br.com.mgobo.profileapp.web.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.UUID;

import static br.com.mgobo.profileapp.api.parsers.CustomerSerialize.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;
    private MockMvc mockMvc = null;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

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

    @Test
    public void testSave() throws Exception {
        CustomerDto customerDto = new CustomerDto(null, "", "RUA TESTE 1", "mateusgobo@gmail.com", "123");
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
}
