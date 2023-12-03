package org.example.money.aggregation.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoney {

    private Long membershipId;
    private int balance;
}
