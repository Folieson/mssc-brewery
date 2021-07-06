package com.folison.msscBrewery.web.controller;

import com.folison.msscBrewery.services.BeerService;
import com.folison.msscBrewery.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor()
public class BeerController {
  private final BeerService beerService;

  @GetMapping("/{beerId}")
  public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId) {
    return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> handlePost(BeerDto beerDto) {
    BeerDto savedDto = beerService.saveNewBeer(beerDto);
    HttpHeaders headers = new HttpHeaders();
    //TODO: add hostname to url
    headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + savedDto.id().toString());
    return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
  }
}
