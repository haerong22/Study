package com.example.rental.adapter.in.web;

import com.example.rental.adapter.in.web.response.RentItemResponse;
import com.example.rental.adapter.in.web.response.RentalCardResponse;
import com.example.rental.adapter.in.web.response.RentalResultResponse;
import com.example.rental.adapter.in.web.response.ReturnItemResponse;
import com.example.rental.application.port.in.*;
import com.example.rental.application.port.in.command.*;
import com.example.rental.domain.model.RentalCard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final CreateRentalCardUseCase createRentalCardUseCase;
    private final InquiryUseCase inquiryUseCase;
    private final RentItemUseCase rentItemUseCase;
    private final ReturnItemUseCase returnItemUseCase;
    private final OverdueItemUseCase overdueItemUseCase;
    private final ClearOverdueItemUseCase clearOverdueItemUseCase;

    @PostMapping("/api/v1/rentalCard")
    public ResponseEntity<RentalCardResponse> createRentalCard(@RequestBody CreateRentalCardCommand request) {

        RentalCard rentalCard = createRentalCardUseCase.createRentalCard(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(RentalCardResponse.toResponse(rentalCard));
    }

    @GetMapping("/api/v1/rentalCard/{userId}")
    public ResponseEntity<RentalCardResponse> getRentalCard(@PathVariable String userId) {
        RentalCard rentalCard = inquiryUseCase.getRentalCard(
                InquiryCommand.builder()
                        .userId(userId)
                        .build()
        );

        return ResponseEntity.ok(
                rentalCard == null ? null : RentalCardResponse.toResponse(rentalCard)
        );
    }

    @GetMapping("/api/v1/rentalCard/{userId}/rentBook")
    public ResponseEntity<List<RentItemResponse>> getAllRentItem(@PathVariable String userId) {
        List<RentItemResponse> response = inquiryUseCase.getAllRentItem(
                InquiryCommand.builder()
                        .userId(userId)
                        .build()
        ).stream()
                .map(RentItemResponse::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/rentalCard/{userId}/returnBook")
    public ResponseEntity<List<ReturnItemResponse>> getAllReturnItem(@PathVariable String userId) {
        List<ReturnItemResponse> response = inquiryUseCase.getAllReturnItem(
                        InquiryCommand.builder()
                                .userId(userId)
                                .build()
                ).stream()
                .map(ReturnItemResponse::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/rentalCard/rent")
    public ResponseEntity<RentalCardResponse> rentItem(@RequestBody RentItemCommand command) {
        RentalCard rentalCard = rentItemUseCase.rentItem(command);
        return ResponseEntity.ok(RentalCardResponse.toResponse(rentalCard));
    }

    @PostMapping("/api/v1/rentalCard/return")
    public ResponseEntity<RentalCardResponse> returnItem(@RequestBody ReturnItemCommand command) {
        RentalCard rentalCard = returnItemUseCase.returnItem(command);
        return ResponseEntity.ok(RentalCardResponse.toResponse(rentalCard));
    }

    @PostMapping("/api/v1/rentalCard/overdue")
    public ResponseEntity<RentalCardResponse> overdueItem(@RequestBody OverdueItemCommand command) {
        RentalCard rentalCard = overdueItemUseCase.overdueItem(command);
        return ResponseEntity.ok(RentalCardResponse.toResponse(rentalCard));
    }

    @PostMapping("/api/v1/rentalCard/clearOverdue")
    public ResponseEntity<RentalResultResponse> clearOverdueItem(@RequestBody ClearOverdueItemCommand command) {
        RentalCard rentalCard = clearOverdueItemUseCase.clearOverdue(command);
        return ResponseEntity.ok(RentalResultResponse.toResponse(rentalCard));
    }
}
