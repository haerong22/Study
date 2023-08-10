package org.example.remittance.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import org.example.remittance.adapter.out.persistence.RemittanceRequestMapper;
import org.example.remittance.adapter.out.service.membership.MembershipStatus;
import org.example.remittance.application.port.in.RequestRemittanceCommand;
import org.example.remittance.application.port.in.RequestRemittanceUseCase;
import org.example.remittance.application.port.out.banking.BankingPort;
import org.example.remittance.application.port.out.membership.MembershipPort;
import org.example.remittance.application.port.out.RequestRemittancePort;
import org.example.remittance.application.port.out.money.MoneyInfo;
import org.example.remittance.application.port.out.money.MoneyPort;
import org.example.remittance.domain.RemittanceRequest;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final RemittanceRequestMapper mapper;
    private final MembershipPort membershipPort;
    private final MoneyPort moneyPort;
    private final BankingPort bankingPort;

    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        // 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)
        RemittanceRequestJpaEntity entity = requestRemittancePort.createRemittanceRequestHistory(command);

        // 1. from 멤버십 상태 확인 (membership Svc)
        MembershipStatus membershipStatus = membershipPort.getMembershipStatus(command.getFromMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }

        // 2. 잔액 존재하는지 확인 (money svc)
        MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getFromMembershipId());

        // 잔액이 충분치 않은 경우. -> 충전이 필요한 경우
        if (moneyInfo.getBalance() < command.getAmount()) {
            int rechargeAmount = (int) Math.ceil((command.getAmount() - moneyInfo.getBalance()) / 10000.0) * 10000;
            boolean montyResult = moneyPort.requestMoneyRecharging(command.getFromMembershipId(), rechargeAmount);

            if (!montyResult) {
                return null;
            }
        }

        // 3. 송금 타입 (고객/은행)
        if (command.getRemittanceType() == 0) {
            boolean remittanceResult1 =
                    moneyPort.requestMoneyDecrease(command.getFromMembershipId(), command.getAmount());
            boolean remittanceResult2 =
                    moneyPort.requestMoneyIncrease(command.getToMembershipId(), command.getAmount());

            if (!remittanceResult1 || !remittanceResult2) {
                return null;
            }

        } else if (command.getRemittanceType() == 1) {
            boolean remittanceResult =
                    bankingPort.requestFirmbanking(command.getToBankName(), command.getToBankAccountNumber(), command.getAmount());

            if (!remittanceResult) {
                return null;
            }
        }

        // 4. 송금 요청 상태를 성공으로 기록 (persistence layer)
        entity.setRemittanceStatus("success");
        boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);

        if (result) {
            return mapper.mapToDomainEntity(entity);
        }

        return null;
    }
}