package com.folison.msscBrewery.services;

import com.folison.msscBrewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
  BeerDto getBeerById(UUID beerId);

  BeerDto saveNewBeer(BeerDto beerDto);
}
