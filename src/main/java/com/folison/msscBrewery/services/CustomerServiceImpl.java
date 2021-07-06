package com.folison.msscBrewery.services;

import com.folison.msscBrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
  @Override
  public CustomerDto getCustomerById(UUID customerId) {
    return new CustomerDto(UUID.randomUUID(), "John");
  }

  @Override
  public CustomerDto saveNewCustomer(CustomerDto customerDto) {
    return new CustomerDto(UUID.randomUUID(), "Foo");
  }

  @Override
  public void updateCustomer(UUID customerId, CustomerDto customerDto) {
    //TODO: impl - would add a real impl to update customer
  }

  @Override
  public void deleteCustomerById(UUID customerId) {
    log.debug("Deleting a customer...");
  }
}
