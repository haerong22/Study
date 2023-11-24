package com.example.rental.application.sevice;

import com.example.rental.application.port.in.InquiryUseCase;
import com.example.rental.application.port.in.command.InquiryCommand;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.RentalItem;
import com.example.rental.domain.model.vo.ReturnItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService implements InquiryUseCase {

    private final RentalCardPort rentalCardPort;

    @Override
    public RentalCard getRentalCard(InquiryCommand command) {
        return rentalCardPort.findRentalCard(command.getUserId())
                .orElse(null);
    }

    @Override
    public List<RentalItem> getAllRentItem(InquiryCommand command) {
        RentalCard rentalCard = rentalCardPort.findRentalCard(command.getUserId()).orElse(null);
        return rentalCard == null ? new ArrayList<>() : rentalCard.getRentalItems();
    }

    @Override
    public List<ReturnItem> getAllReturnItem(InquiryCommand command) {
        RentalCard rentalCard = rentalCardPort.findRentalCard(command.getUserId()).orElse(null);
        return rentalCard == null ? new ArrayList<>() : rentalCard.getReturnItems();
    }
}
