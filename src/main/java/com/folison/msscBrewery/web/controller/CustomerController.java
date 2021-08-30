package com.folison.msscBrewery.web.controller;

import com.folison.msscBrewery.services.CustomerService;
import com.folison.msscBrewery.web.model.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {
  private final CustomerService customerService;

  @GetMapping("/{customerId}")
  public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID customerId) {
    return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> handlePost(@Valid @RequestBody CustomerDto customerDto) {
    CustomerDto savedDto = customerService.saveNewCustomer(customerDto);
    HttpHeaders headers = new HttpHeaders();
    //TODO: add hostname to url
    headers.add(HttpHeaders.LOCATION, "/api/v1/customer/" + savedDto.getId().toString());
    return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
  }

  @PutMapping("/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void handleUpdate(@PathVariable("customerId") UUID customerId,
                                             @Valid @RequestBody CustomerDto customerDto) {
    customerService.updateCustomer(customerId, customerDto);
  }

  @DeleteMapping("/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCustomer(@PathVariable("customerId") UUID customerId) {
    customerService.deleteCustomerById(customerId);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException exception) {
    List<String> errors = new ArrayList<>(exception.getConstraintViolations().size());
    exception.getConstraintViolations().forEach(constraintViolation ->
        errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage()));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
