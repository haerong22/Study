package org.example.remittance.application.service;

import org.example.remittance.adapter.out.persistence.RemittanceRequestMapper;
import org.example.remittance.adapter.out.service.membership.MembershipStatus;
import org.example.remittance.application.port.in.RequestRemittanceCommand;
import org.example.remittance.application.port.out.RequestRemittancePort;
import org.example.remittance.application.port.out.banking.BankingPort;
import org.example.remittance.application.port.out.membership.MembershipPort;
import org.example.remittance.application.port.out.money.MoneyInfo;
import org.example.remittance.application.port.out.money.MoneyPort;
import org.example.remittance.domain.RemittanceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class RequestRemittanceServiceTest {

    @InjectMocks
    private RequestRemittanceService requestRemittanceService;

    @Mock
    private RequestRemittancePort requestRemittancePort;

    @Mock
    private RemittanceRequestMapper mapper;

    @Mock
    private MembershipPort membershipPort;

    @Mock
    private MoneyPort moneyPort;

    @Mock
    private BankingPort bankingPort;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        requestRemittanceService
                = new RequestRemittanceService(requestRemittancePort, mapper, membershipPort, moneyPort, bankingPort);
    }

    private static Stream<RequestRemittanceCommand> provideRequestRemittanceCommand() {
        return Stream.of(
                RequestRemittanceCommand.builder()
                        .fromMembershipId("5")
                        .toMembershipId("4")
                        .toBankName("bank")
                        .remittanceType(0)
                        .toBankAccountNumber("1234-5678-890")
                        .amount(150000)
                        .build()
        );
    }

    @ParameterizedTest
    @MethodSource("provideRequestRemittanceCommand")
    void testRequestRemittanceWhenMembershipInvalid(RequestRemittanceCommand testCommand) {
        // given
        when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
                .thenReturn(null);
        when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
                .thenReturn(new MembershipStatus(testCommand.getFromMembershipId(), false));

        // when
        RemittanceRequest result = requestRemittanceService.requestRemittance(testCommand);

        // then
        verify(requestRemittancePort, times(1)).createRemittanceRequestHistory(testCommand);
        verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());

        assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("provideRequestRemittanceCommand")
    public void testRequestRemittanceWhenNotEnoughMoney(RequestRemittanceCommand testCommand){
        // given
        MoneyInfo dummyMoneyInfo = new MoneyInfo(testCommand.getFromMembershipId(), 1000) ;

        when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
                .thenReturn(null);
        when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
                .thenReturn(new MembershipStatus(testCommand.getFromMembershipId(), true));
        when(moneyPort.getMoneyInfo(testCommand.getFromMembershipId()))
                .thenReturn(new MoneyInfo(testCommand.getFromMembershipId(), 1000));

        int rechargeAmount = (int) Math.ceil((testCommand.getAmount() - dummyMoneyInfo.getBalance()) / 10000.0) * 10000;
        when(moneyPort.requestMoneyRecharging(testCommand.getFromMembershipId(), rechargeAmount))
                .thenReturn(false);

        // when
        RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

        // then
        verify(requestRemittancePort, times(1)).createRemittanceRequestHistory(testCommand);
        verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());
        verify(moneyPort, times(1)).getMoneyInfo(testCommand.getFromMembershipId());
        verify(moneyPort, times(1)).requestMoneyRecharging(testCommand.getFromMembershipId(), rechargeAmount);

        // 6. assert
        assertNull(got);
    }
}