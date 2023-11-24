package com.example.rental.application.port.out;

import com.example.rental.domain.model.RentalCard;

import java.util.Optional;

public interface RentalCardPort {

    Optional<RentalCard> findRentalCard(String userId);

    RentalCard save(RentalCard rentalCard);
}
