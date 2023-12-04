package org.example.money.query.adapter.out.aws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneySumByAddress {
    private String PK;
    private String SK;
    private int balance;
}