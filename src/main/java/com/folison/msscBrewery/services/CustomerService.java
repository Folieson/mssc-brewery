package com.folison.msscBrewery.services;

import com.folison.msscBrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
  CustomerDto getCustomerById(UUID customerId);

  CustomerDto saveNewCustomer(CustomerDto customerDto);

  void updateCustomer(UUID customerId, CustomerDto customerDto);

  void deleteCustomerById(UUID customerId);
}
