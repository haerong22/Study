package org.example.banking.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegisteredBankAccountEvent {

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;
}
