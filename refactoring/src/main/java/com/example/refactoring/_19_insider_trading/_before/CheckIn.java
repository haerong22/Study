package com.example.refactoring._19_insider_trading._before;

import com.example.refactoring._19_insider_trading.Ticket;

import java.time.LocalDate;

public class CheckIn {

    public boolean isFastPass(Ticket ticket) {
        LocalDate earlyBirdDate = LocalDate.of(2022, 1, 1);
        return ticket.isPrime() && ticket.getPurchasedDate().isBefore(earlyBirdDate);
    }
}
