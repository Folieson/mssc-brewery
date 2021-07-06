package com.folison.msscBrewery.web.model;

import java.util.UUID;

public record BeerDto(UUID id, String beerName, String beerStyle, Long upc) {
}
