package org.example.paymentservice.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.wallet.WalletLockerService;
import org.example.paymentservice.wallet.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockedTransactionService {
    private final WalletService walletService;
    private final WalletLockerService walletLockerService;
    private final TransactionService transactionService;

    @Transactional
    public ChargeTransactionResponse charge(ChargeTransactionRequest request) {
        final var findWallerResponse = walletService.findWalletByUserId(request.userId());
        final var lock = walletLockerService.acquireLock(findWallerResponse.id());

        if (lock == null) {
            throw new IllegalStateException("cannot get lock");
        }

        try {
            return transactionService.charge(request);
        } finally {
            walletLockerService.releaseLock(lock);
        }
    }
}
