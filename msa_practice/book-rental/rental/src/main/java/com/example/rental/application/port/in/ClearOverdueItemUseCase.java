package com.example.rental.application.port.in;

import com.example.rental.application.port.in.command.ClearOverdueItemCommand;
import com.example.rental.domain.model.RentalCard;

public interface ClearOverdueItemUseCase {

    RentalCard clearOverdue(ClearOverdueItemCommand command);
}
