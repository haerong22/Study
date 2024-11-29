package org.example.paymentservice.wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final BigDecimal BALANCE_LIMIT = BigDecimal.valueOf(100_000_000);

    private final WalletRepository walletRepository;

    @Transactional
    public CreateWalletResponse createWallet(CreateWalletRequest request) {
        boolean isWalletExist = walletRepository.findByUserId(request.userId()).isPresent();
        if (isWalletExist) {
            throw new RuntimeException("이미 지갑이 있습니다.");
        }

        final Wallet wallet = walletRepository.save(new Wallet(request.userId()));
        return new CreateWalletResponse(wallet.getId(), wallet.getUserId(), wallet.getBalance());
    }

    public FindWalletResponse findWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId)
                .map(wallet -> new FindWalletResponse(
                        wallet.getId(), wallet.getUserId(), wallet.getBalance(), wallet.getCreatedAt(), wallet.getUpdatedAt()
                ))
                .orElse(null);
    }

    public FindWalletResponse findWalletByWalletId(Long walletId) {
        return walletRepository.findById(walletId)
                .map(wallet -> new FindWalletResponse(
                        wallet.getId(), wallet.getUserId(), wallet.getBalance(), wallet.getCreatedAt(), wallet.getUpdatedAt()
                ))
                .orElse(null);
    }

    @Transactional
    public AddBalanceWalletResponse addBalance(AddBalanceWalletRequest request) {
        final Wallet wallet = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new RuntimeException("지갑이 없습니다."));

        BigDecimal balance = wallet.getBalance();
        balance = balance.add(request.amount());

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("잔액이 충분하지 않습니다.");
        }

        if (BALANCE_LIMIT.compareTo(balance) < 0) {
            throw new RuntimeException("한도를 초과했습니다.");
        }

        wallet.setBalance(balance);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);
        return new AddBalanceWalletResponse(
                wallet.getId(), wallet.getUserId(), wallet.getBalance()
        );
    }
}
