package com.example.rental.application.sevice;

import com.example.rental.application.port.in.OverdueItemUseCase;
import com.example.rental.application.port.in.command.OverdueItemCommand;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OverdueItemService implements OverdueItemUseCase {

    private final RentalCardPort rentalCardPort;

    @Override
    public RentalCard overdueItem(OverdueItemCommand command) {
        RentalCard rentalCard = rentalCardPort.findRentalCard(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 없습니다."));

        Item item = Item.create(command.getItemId(), command.getItemTitle());
        rentalCard.overdueItem(item);

        return rentalCardPort.save(rentalCard);
    }
}
