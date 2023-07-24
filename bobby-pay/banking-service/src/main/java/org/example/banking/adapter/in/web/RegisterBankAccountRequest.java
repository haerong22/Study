package org.example.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBankAccountRequest {

    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean isValid;
}
