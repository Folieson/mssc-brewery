package com.folison.msscBrewery.web.controller;

import com.folison.msscBrewery.services.BeerService;
import com.folison.msscBrewery.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
  public ResponseEntity<String> handlePost(@Valid @RequestBody BeerDto beerDto) {
    BeerDto savedDto = beerService.saveNewBeer(beerDto);
    HttpHeaders headers = new HttpHeaders();
    //TODO: add hostname to url
    headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + savedDto.getId().toString());
    return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
  }

  @PutMapping("/{beerId}")
  public ResponseEntity<String> handleUpdate(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto) {
    beerService.updateBeer(beerId, beerDto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{beerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBeer(@PathVariable("beerId") UUID beerId) {
    beerService.deleteBeerById(beerId);
  }
}
