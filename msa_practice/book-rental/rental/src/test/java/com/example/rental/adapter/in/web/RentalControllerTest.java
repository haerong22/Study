package com.example.rental.adapter.in.web;

import com.example.rental.application.port.in.CreateRentalCardUseCase;
import com.example.rental.application.port.in.command.CreateRentalCardCommand;
import com.example.rental.domain.model.RentalCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {RentalController.class})
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CreateRentalCardUseCase createRentalCardUseCase;

    @Test
    @DisplayName("RentalCard를 생성한다.")
    void createRentalCard() throws Exception {
        // given
        CreateRentalCardCommand request = CreateRentalCardCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        when(createRentalCardUseCase.createRentalCard(any(CreateRentalCardCommand.class)))
                .thenReturn(new RentalCard());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/rentalCard")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rentalCardId").value("0"))
                .andExpect(jsonPath("$.memberId").value("0"))
                .andExpect(jsonPath("$.memberName").value("test"))
                .andExpect(jsonPath("$.rentStatus").value("RENT_AVAILABLE"))
                .andExpect(jsonPath("$.totalLateFee").value(0))
                .andExpect(jsonPath("$.totalRentalCnt").value(0))
                .andExpect(jsonPath("$.totalReturnCnt").value(0))
                .andExpect(jsonPath("$.totalOverduedCnt").value(0))
        ;

    }
}