package org.example.money.query.adapter.axon;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.common.event.RequestFirmBankingFinishedEvent;
import org.example.money.query.application.port.out.GetMemberAddressInfoPort;
import org.example.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import org.example.money.query.application.port.out.MemberAddressInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MoneyIncreaseEventHandler {

    @EventHandler
    public void handler(
            RequestFirmBankingFinishedEvent event,
            GetMemberAddressInfoPort getMemberAddressInfoPort,
            InsertMoneyIncreaseEventByAddress insertMoneyIncreaseEventByAddress
    ) {
        log.info("Money Increase Event Received: {}", event);

        MemberAddressInfo memberAddressInfo = getMemberAddressInfoPort.getMemberAddressInfo(event.getMembershipId());

        String address = memberAddressInfo.getAddress();
        int moneyIncrease = event.getAmount();

        log.info("DynamoDB Insert: {}, {}", address, moneyIncrease);

        insertMoneyIncreaseEventByAddress.insertMoneyIncreaseEventByAddress(address, moneyIncrease);
    }
}
