package com.example.rental.application.sevice;

import com.example.rental.application.port.in.ReturnItemUseCase;
import com.example.rental.application.port.in.command.ReturnItemCommand;
import com.example.rental.application.port.out.EventPort;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.event.ItemReturned;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ReturnItemService implements ReturnItemUseCase {

    private final RentalCardPort rentalCardPort;
    private final EventPort eventPort;

    @Override
    public RentalCard returnItem(ReturnItemCommand command) {
        RentalCard rentalCard = rentalCardPort.findRentalCard(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 없습니다."));

        Item returnItem = Item.create(command.getItemId(), command.getItemTitle());
        rentalCard.returnItem(returnItem, LocalDate.now());

        RentalCard saved = rentalCardPort.save(rentalCard);

        ItemReturned event = RentalCard.createItemReturnEvent(rentalCard.getMember(), returnItem, 10L);
        eventPort.returnEvent(event);

        return saved;
    }
}
