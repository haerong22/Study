package com.example.rental.application.port.out;

import com.example.rental.domain.event.ItemRented;
import com.example.rental.domain.event.ItemReturned;
import com.example.rental.domain.event.OverdueCleared;

public interface EventPort {

    void rentalEvent(ItemRented event);
    void returnEvent(ItemReturned event);
    void overdueClearEvent(OverdueCleared event);
}
