package com.example.rental.application.sevice;

import com.example.rental.application.port.in.CreateRentalCardUseCase;
import com.example.rental.application.port.in.command.CreateRentalCardCommand;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.IDName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateRentalCardService implements CreateRentalCardUseCase {

    private final RentalCardPort rentalCardPort;

    @Override
    public RentalCard createRentalCard(CreateRentalCardCommand command) {
        IDName owner = IDName.create(command.getUserId(), command.getUserNm());
        RentalCard rentalCard = RentalCard.createRentalCard(owner);
        return rentalCardPort.save(rentalCard);
    }
}
