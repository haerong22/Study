package com.example.rental.application.port.in;

import com.example.rental.application.port.in.command.InquiryCommand;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.RentalItem;
import com.example.rental.domain.model.vo.ReturnItem;

import java.util.List;

public interface InquiryUseCase {

    RentalCard getRentalCard(InquiryCommand command);

    List<RentalItem> getAllRentItem(InquiryCommand command);

    List<ReturnItem> getAllReturnItem(InquiryCommand command);

}
