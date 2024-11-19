package org.example.paymentservice.wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WalletService {

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
}
