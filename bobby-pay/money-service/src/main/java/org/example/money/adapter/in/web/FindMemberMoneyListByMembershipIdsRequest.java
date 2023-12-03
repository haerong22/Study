package org.example.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMemberMoneyListByMembershipIdsRequest {

    private List<Long> membershipId;

}
