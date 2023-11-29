package com.example.rental.application.port.in;

import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.IDName;
import com.example.rental.domain.model.vo.Item;

public interface CompensationUseCase {

    RentalCard cancelRentItem(IDName idName, Item item);
    RentalCard cancelReturnItem(IDName idName, Item item, long point);
    long cancelMakeAvailableRental(IDName idName, long point);
}
