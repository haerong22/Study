package org.example.banking.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "registered_bank_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankAccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registeredBankAccountId;
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean linkedStatusIsValid;

    private String aggregateIdentifier;

    public RegisteredBankAccountJpaEntity(String membershipId, String bankName, String bankAccountNumber, boolean linkedStatusIsValid, String aggregateIdentifier) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.linkedStatusIsValid = linkedStatusIsValid;
        this.aggregateIdentifier = aggregateIdentifier;
    }
}
