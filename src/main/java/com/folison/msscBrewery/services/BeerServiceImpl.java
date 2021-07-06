package com.folison.msscBrewery.services;

import com.folison.msscBrewery.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
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

  @Override
  public void updateBeer(UUID beerId, BeerDto beerDto) {
    //TODO: impl - would add a real ipl to update beer
  }

  @Override
  public void deleteBeerById(UUID beerId) {
    log.debug("Deleting a beer...");
  }
}
