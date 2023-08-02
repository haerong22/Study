package org.example.money.application.port.in;

import org.example.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
