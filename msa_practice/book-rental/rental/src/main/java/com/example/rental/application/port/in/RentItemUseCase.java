package com.example.rental.application.port.in;

import com.example.rental.application.port.in.command.RentItemCommand;
import com.example.rental.domain.model.RentalCard;

public interface RentItemUseCase {

    RentalCard rentItem(RentItemCommand command);
}
