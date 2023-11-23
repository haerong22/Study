package com.example.rental.application.port.in;

import com.example.rental.application.port.in.command.OverdueItemCommand;
import com.example.rental.domain.model.RentalCard;

public interface OverdueItemUseCase {

    RentalCard overdueItem(OverdueItemCommand command);
}
