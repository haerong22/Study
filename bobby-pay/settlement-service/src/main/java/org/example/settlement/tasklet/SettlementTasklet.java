package org.example.settlement.tasklet;

import lombok.RequiredArgsConstructor;
import org.example.settlement.adapter.out.service.Payment;
import org.example.settlement.job.FirmBankingRequestInfo;
import org.example.settlement.port.out.GetRegisteredBankAccountPort;
import org.example.settlement.port.out.PaymentPort;
import org.example.settlement.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SettlementTasklet implements Tasklet {

    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final PaymentPort paymentPort;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){

        // 결제 완료 내역 조회
        List<Payment> paymentComplete = paymentPort.getPaymentComplete();

        // 계좌 번호 조회
        Map<String, FirmBankingRequestInfo> bankAccountMap = paymentComplete.stream().collect(Collectors.toMap(
                Payment::getFranchiseId,
                p -> {
                    RegisteredBankAccountAggregateIdentifier bankAccount = getRegisteredBankAccountPort.getRegisteredBankAccount(p.getRequestMembershipId());
                    return new FirmBankingRequestInfo(bankAccount.getBankName(), bankAccount.getBankAccountNumber());
                }
        ));

        // 정산 금액 계산
        paymentComplete.forEach(p -> {
            FirmBankingRequestInfo bankingRequestInfo = bankAccountMap.get(p.getFranchiseId());

            int calculated = (int) ((100 - Double.parseDouble(p.getFranchiseFeeRate())) * p.getRequestPrice() / 100);

            bankingRequestInfo.setMoneyAmount(bankingRequestInfo.getMoneyAmount() + calculated);
        });

        // 펌 뱅킹 요청
        bankAccountMap.values().forEach(b -> {
            getRegisteredBankAccountPort.requestFirmBanking(
                    b.getBankName(),
                    b.getBankAccountNumber(),
                    b.getMoneyAmount()
            );
        });

        // 정산 완료 상태 변경
        paymentComplete.forEach(p -> paymentPort.settlement(p.getPaymentId()));

        return RepeatStatus.FINISHED;
    }
}