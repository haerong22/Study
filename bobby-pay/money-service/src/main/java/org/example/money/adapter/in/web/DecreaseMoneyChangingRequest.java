package org.example.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecreaseMoneyChangingRequest {

    private String targetMembershipId;
    private int amount;

}
