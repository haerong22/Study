package com.example.rental.adapter.in.web;

import com.example.rental.adapter.in.web.response.RentalCardResponse;
import com.example.rental.application.port.in.CreateRentalCardUseCase;
import com.example.rental.application.port.in.command.CreateRentalCardCommand;
import com.example.rental.domain.model.RentalCard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final CreateRentalCardUseCase createRentalCardUseCase;

    @PostMapping("/api/v1/rentalCard")
    public ResponseEntity<RentalCardResponse> createRentalCard(@RequestBody CreateRentalCardCommand request) {

        RentalCard rentalCard = createRentalCardUseCase.createRentalCard(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(RentalCardResponse.toResponse(rentalCard));
    }

}
