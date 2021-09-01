package com.folison.msscBrewery.web.mappers;

import com.folison.msscBrewery.domain.Customer;
import com.folison.msscBrewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
  CustomerDto customerToCustomerDto(Customer customer);
  Customer customerDtoToCustomer(CustomerDto customerDto);
}
