package com.example.rental.adapter.in.web;

import com.example.rental.adapter.in.web.response.RentalCardResponse;
import com.example.rental.application.port.in.*;
import com.example.rental.application.port.in.command.CreateRentalCardCommand;
import com.example.rental.application.port.in.command.InquiryCommand;
import com.example.rental.domain.model.RentalCard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final CreateRentalCardUseCase createRentalCardUseCase;
    private final InquiryUseCase inquiryUseCase;

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
}
