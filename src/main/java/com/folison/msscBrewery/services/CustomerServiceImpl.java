package com.folison.msscBrewery.services;

import com.folison.msscBrewery.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
  @Override
  public CustomerDto getCustomerById(UUID customerId) {
    return new CustomerDto(UUID.randomUUID(), "John");
  }
}
