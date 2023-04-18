package com.zerobase.reservation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import com.zerobase.reservation.service.RestaurantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("매장 등록")
    public void addRestaurant() throws Exception {

        String name = "restaurant01";
        String description = "일식 전문점입니다.";
        String phoneNumber = "010-1111-1111";
        String address = "서울시 강남구";

        given(restaurantService.addRestaurant(any()))
            .willReturn(
                RestaurantCreateResponseDto.fromEntity(
                    Restaurant.builder()
                        .name(name)
                        .description(description)
                        .build())
            );

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new RestaurantCreateRequestDto(name, phoneNumber, address, description)
                )))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.description").value(description))
            .andDo(print());

        verify(restaurantService).addRestaurant(any());

    }

    @Test
    @DisplayName("매장 조회")
    void getAllRestaurant() {

        // given

        // when

        // then
    }


}