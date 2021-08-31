package com.folison.msscBrewery.web.mappers;

import com.folison.msscBrewery.domain.Beer;
import com.folison.msscBrewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
  BeerDto beerToBeerDto(Beer beer);
  Beer beerDtoToBeer(BeerDto beerDto);
}
