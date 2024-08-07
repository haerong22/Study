package com.example.rental.adapter.in.web;

import com.example.rental.application.port.in.*;
import com.example.rental.application.port.in.command.*;
import com.example.rental.domain.model.RentalCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {RentalController.class})
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CreateRentalCardUseCase createRentalCardUseCase;

    @MockBean
    private RentItemUseCase rentItemUseCase;

    @MockBean
    private InquiryUseCase inquiryUseCase;

    @MockBean
    private ReturnItemUseCase returnItemUseCase;

    @MockBean
    private OverdueItemUseCase overdueItemUseCase;

    @MockBean
    private ClearOverdueItemUseCase clearOverdueItemUseCase;

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

        verify(createRentalCardUseCase).createRentalCard(any(CreateRentalCardCommand.class));
    }

    @Test
    @DisplayName("특정 RentalCard를 조회한다.")
    void getRentalCard() throws Exception {
        // given
        when(inquiryUseCase.getRentalCard(any(InquiryCommand.class)))
                .thenReturn(new RentalCard());

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/rentalCard/0")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentalCardId").value("0"))
                .andExpect(jsonPath("$.memberId").value("0"))
                .andExpect(jsonPath("$.memberName").value("test"))
                .andExpect(jsonPath("$.rentStatus").value("RENT_AVAILABLE"))
                .andExpect(jsonPath("$.totalLateFee").value(0))
                .andExpect(jsonPath("$.totalRentalCnt").value(0))
                .andExpect(jsonPath("$.totalReturnCnt").value(0))
                .andExpect(jsonPath("$.totalOverduedCnt").value(0))
        ;

        verify(inquiryUseCase).getRentalCard(any(InquiryCommand.class));
    }

    @Test
    @DisplayName("RentalCard가 없으면 null 응답한다.")
    void getRentalCardWithNull() throws Exception {
        // given
        when(inquiryUseCase.getRentalCard(any(InquiryCommand.class))).thenReturn(null);

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/rentalCard/0")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""))
        ;

        verify(inquiryUseCase).getRentalCard(any(InquiryCommand.class));
    }

    @Test
    @DisplayName("대여중인 전체 도서를 조회한다.")
    void getAllRentItem() throws Exception {
        // given
        when(inquiryUseCase.getAllRentItem(any(InquiryCommand.class)))
                .thenReturn(List.of());

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/rentalCard/0/rentBook")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
        ;

        verify(inquiryUseCase).getAllRentItem(any(InquiryCommand.class));
    }

    @Test
    @DisplayName("반납완료한 전체 도서를 조회한다.")
    void getAllReturnItem() throws Exception {
        // given
        when(inquiryUseCase.getAllReturnItem(any(InquiryCommand.class)))
                .thenReturn(List.of());

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/rentalCard/0/returnBook")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
        ;

        verify(inquiryUseCase).getAllReturnItem(any(InquiryCommand.class));
    }

    @Test
    @DisplayName("도서를 대여한다.")
    void rentItem() throws Exception {
        // given
        RentItemCommand request = RentItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        when(rentItemUseCase.rentItem(any(RentItemCommand.class)))
                .thenReturn(new RentalCard());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/rentalCard/rent")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        verify(rentItemUseCase).rentItem(any(RentItemCommand.class));
    }

    @Test
    @DisplayName("도서를 반납한다.")
    void returnItem() throws Exception {
        // given
        ReturnItemCommand request = ReturnItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        when(returnItemUseCase.returnItem(any(ReturnItemCommand.class)))
                .thenReturn(new RentalCard());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/rentalCard/return")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        verify(returnItemUseCase).returnItem(any(ReturnItemCommand.class));
    }

    @Test
    @DisplayName("연체 처리 한다.")
    void overdueItem() throws Exception {
        // given
        OverdueItemCommand request = OverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        when(overdueItemUseCase.overdueItem(any(OverdueItemCommand.class)))
                .thenReturn(new RentalCard());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/rentalCard/overdue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        verify(overdueItemUseCase).overdueItem(any(OverdueItemCommand.class));
    }

    @Test
    @DisplayName("연체 상태를 해제한다.")
    void clearOverdueItem() throws Exception {
        // given
        ClearOverdueItemCommand request = ClearOverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .point(100)
                .build();

        when(clearOverdueItemUseCase.clearOverdue(any(ClearOverdueItemCommand.class)))
                .thenReturn(new RentalCard());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/rentalCard/clearOverdue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        verify(clearOverdueItemUseCase).clearOverdue(any(ClearOverdueItemCommand.class));
    }
}