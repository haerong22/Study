package com.example.rental.application.sevice;

import com.example.rental.application.port.in.ClearOverdueItemUseCase;
import com.example.rental.application.port.in.command.ClearOverdueItemCommand;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.model.RentalCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClearOverdueItemService implements ClearOverdueItemUseCase {

    private final RentalCardPort rentalCardPort;

    @Override
    public RentalCard clearOverdue(ClearOverdueItemCommand command) {
        RentalCard rentalCard = rentalCardPort.findRentalCard(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 없습니다."));

        rentalCard.makeAvailableRental(command.getPoint());

        return rentalCardPort.save(rentalCard);
    }

}
