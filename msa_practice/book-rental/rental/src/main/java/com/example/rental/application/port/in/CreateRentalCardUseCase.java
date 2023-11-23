package com.example.rental.application.port.in;

import com.example.rental.application.port.in.command.CreateRentalCardCommand;
import com.example.rental.domain.model.RentalCard;

public interface CreateRentalCardUseCase {

    RentalCard createRentalCard(CreateRentalCardCommand command);
}
