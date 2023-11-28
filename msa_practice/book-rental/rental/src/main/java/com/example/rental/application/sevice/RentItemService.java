package com.example.rental.application.sevice;

import com.example.rental.application.port.in.RentItemUseCase;
import com.example.rental.application.port.in.command.RentItemCommand;
import com.example.rental.application.port.out.EventPort;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.event.ItemRented;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.IDName;
import com.example.rental.domain.model.vo.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class RentItemService implements RentItemUseCase {

    private final RentalCardPort rentalCardPort;
    private final EventPort eventPort;

    @Override
    public RentalCard rentItem(RentItemCommand command) {
        RentalCard rentalCard = rentalCardPort.findRentalCard(command.getUserId())
                .orElseGet(() -> RentalCard.createRentalCard(IDName.create(command.getUserId(), command.getUserNm())));

        Item newItem = Item.create(command.getItemId(), command.getItemTitle());
        rentalCard.rentItem(newItem, LocalDate.now());

        RentalCard saved = rentalCardPort.save(rentalCard);

        ItemRented event = RentalCard.createItemRentedEvent(rentalCard.getMember(), newItem, 10L);
        eventPort.rentalEvent(event);

        return saved;
    }
}
