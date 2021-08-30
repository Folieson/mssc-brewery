package com.folison.msscBrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folison.msscBrewery.services.CustomerService;
import com.folison.msscBrewery.web.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  private static final UUID CUSTOMER_ID = UUID.randomUUID();
  private static final String CUSTOMER_NAME = "John";
  private static final String URL = "/api/v1/customer/";
  private CustomerDto validCustomer;

  @MockBean
  private CustomerService customerService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    validCustomer = new CustomerDto(CUSTOMER_ID, CUSTOMER_NAME);
  }

  @Test
  void getCustomer() throws Exception {
    given(customerService.getCustomerById(any(UUID.class))).willReturn(validCustomer);

    mockMvc.perform(get(URL + CUSTOMER_ID).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(CUSTOMER_ID.toString())))
        .andExpect(jsonPath("$.name", is(CUSTOMER_NAME)));
  }

  @Test
  void handlePost() throws Exception {
    CustomerDto customerDto = new CustomerDto(null, CUSTOMER_NAME);

    CustomerDto savedDto = new CustomerDto(UUID.randomUUID(), "New Customer");
    String customerDtoJson = objectMapper.writeValueAsString(customerDto);

    given(customerService.saveNewCustomer(eq(customerDto))).willReturn(savedDto);

    mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(customerDtoJson))
        .andExpect(status().isCreated());
  }

  @Test
  void handleUpdate() throws Exception {
    validCustomer.setId(null);
    String customerDtoJson = objectMapper.writeValueAsString(validCustomer);

    mockMvc.perform(put(URL + CUSTOMER_ID).contentType(MediaType.APPLICATION_JSON).content(customerDtoJson))
        .andExpect(status().isNoContent());

    then(customerService).should().updateCustomer(eq(CUSTOMER_ID), eq(validCustomer));
  }

  @Test
  void deleteCustomer() throws Exception {
    mockMvc.perform(delete(URL + CUSTOMER_ID).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    then(customerService).should().deleteCustomerById(CUSTOMER_ID);
  }
}