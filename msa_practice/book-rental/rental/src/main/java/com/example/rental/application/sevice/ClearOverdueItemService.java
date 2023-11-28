package com.example.rental.application.sevice;

import com.example.rental.application.port.in.ClearOverdueItemUseCase;
import com.example.rental.application.port.in.command.ClearOverdueItemCommand;
import com.example.rental.application.port.out.EventPort;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.event.OverdueCleared;
import com.example.rental.domain.model.RentalCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClearOverdueItemService implements ClearOverdueItemUseCase {

    private final RentalCardPort rentalCardPort;
    private final EventPort eventPort;

    @Override
    public RentalCard clearOverdue(ClearOverdueItemCommand command) {
        RentalCard rentalCard = rentalCardPort.findRentalCard(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 없습니다."));

        rentalCard.makeAvailableRental(command.getPoint());

        RentalCard saved = rentalCardPort.save(rentalCard);

        OverdueCleared event = RentalCard.createOverdueClearedEvent(rentalCard.getMember(), 10L);
        eventPort.overdueClearEvent(event);

        return saved;
    }

}
