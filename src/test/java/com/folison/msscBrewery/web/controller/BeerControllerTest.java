package com.folison.msscBrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folison.msscBrewery.services.BeerService;
import com.folison.msscBrewery.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

  private static final UUID BEER_ID = UUID.randomUUID();
  private static final String BEER_NAME = "Beer1";
  private static final String BEER_STYLE = "PALE_ALE";
  private static final long BEER_UPC = 123456789012L;
  private static final BeerDto VALID_BEER = new BeerDto(BEER_ID, BEER_NAME, BEER_STYLE, BEER_UPC);
  private static final String URL = "/api/v1/beer/";

  @MockBean
  private BeerService beerService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;




  @Test
  void getBeer() throws Exception {
    given(beerService.getBeerById(any(UUID.class))).willReturn(VALID_BEER);

    mockMvc.perform(get(URL + BEER_ID).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(BEER_ID.toString())))
        .andExpect(jsonPath("$.beerName", is(BEER_NAME)));
  }

  @Test
  void handlePost() throws Exception {
    BeerDto beerDto = new BeerDto(null, BEER_NAME, BEER_STYLE, BEER_UPC);

    BeerDto savedDto = new BeerDto(UUID.randomUUID(), "New Beer", BEER_STYLE, BEER_UPC);
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.saveNewBeer(eq(beerDto))).willReturn(savedDto);

    mockMvc.perform(post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
        .andExpect(status().isCreated());
  }

  @Test
  void handleUpdate() throws Exception {
    String beerDtoJson = objectMapper.writeValueAsString(VALID_BEER);

    mockMvc.perform(put(URL + BEER_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
        .andExpect(status().isNoContent());

    then(beerService).should().updateBeer(eq(BEER_ID), eq(VALID_BEER));
  }

  @Test
  void deleteBeer() throws Exception {
    mockMvc.perform(delete(URL + BEER_ID)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    then(beerService).should().deleteBeerById(BEER_ID);
  }
}