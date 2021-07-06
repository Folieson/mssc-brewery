package com.folison.msscBrewery.services;

import com.folison.msscBrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
  CustomerDto getCustomerById(UUID customerId);
}
