package org.example.money.application.port.out;

import org.example.common.tasks.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {

    void sendRechargingMoneyTaskPort(
            RechargingMoneyTask task
    );
}