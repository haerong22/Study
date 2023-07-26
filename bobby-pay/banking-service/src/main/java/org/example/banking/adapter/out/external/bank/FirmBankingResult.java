package org.example.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FirmBankingResult {

    private int resultCode; // 0: 성공, 1: 실패
}
