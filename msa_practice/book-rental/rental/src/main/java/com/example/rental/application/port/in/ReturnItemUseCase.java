package com.example.rental.application.port.in;

import com.example.rental.application.port.in.command.ReturnItemCommand;
import com.example.rental.domain.model.RentalCard;

public interface ReturnItemUseCase {

    RentalCard returnItem(ReturnItemCommand command);
}
