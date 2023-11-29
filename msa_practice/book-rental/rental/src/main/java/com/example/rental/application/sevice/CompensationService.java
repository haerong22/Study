package com.example.rental.application.sevice;

import com.example.rental.application.port.in.CompensationUseCase;
import com.example.rental.application.port.out.EventPort;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.event.PointUse;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.IDName;
import com.example.rental.domain.model.vo.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompensationService implements CompensationUseCase {

    private final RentalCardPort rentalCardPort;
    private final EventPort eventPort;

    @Override
    public RentalCard cancelRentItem(IDName idName, Item item) {
        return rentalCardPort.findRentalCard(idName.getId())
                .map(rentalCard -> {
                    try {
                        rentalCardPort.save(rentalCard.cancelRentItem(item));
                        eventPort.pointUseEvent(new PointUse(idName, 10L));
                        return rentalCard;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> {
                    log.error("대여 카드가 없습니다. ID: {}", idName.getId());
                    throw new NoSuchElementException("대여 카드가 없습니다. ID: " + idName.getId());
                });
    }

    @Override
    public RentalCard cancelReturnItem(IDName idName, Item item, long point) {
        return rentalCardPort.findRentalCard(idName.getId())
                .map(rentalCard -> {
                    try {
                        rentalCardPort.save(rentalCard.cancelReturnItem(item, point));
                        eventPort.pointUseEvent(new PointUse(idName, 10L));
                        return rentalCard;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("대여 카드가 없습니다. ID: " + idName.getId()));
    }

    @Override
    public long cancelMakeAvailableRental(IDName idName, long point) {
        return rentalCardPort.findRentalCard(idName.getId())
                .map(rentalCard -> {
                    try {
                        long result = rentalCard.cancelMakeAvailableRental(point);
                        rentalCardPort.save(rentalCard);
                        return result;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("대여 카드가 없습니다. ID: " + idName.getId()));
    }
}
