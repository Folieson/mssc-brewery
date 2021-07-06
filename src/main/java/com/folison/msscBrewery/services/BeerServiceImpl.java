package com.folison.msscBrewery.services;

import com.folison.msscBrewery.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
  @Override
  public BeerDto getBeerById(UUID beerId) {
    return new BeerDto(UUID.randomUUID(), "Galaxy Cat", "Pale Ale", 123L);
  }

  @Override
  public BeerDto saveNewBeer(BeerDto beerDto) {
    return new BeerDto(UUID.randomUUID(), "Foo", "Bar", 321L);
  }
}
