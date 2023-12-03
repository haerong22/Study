package org.example.membership.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.membership.application.port.in.FindMembershipCommand;
import org.example.membership.application.port.in.FindMembershipListByAddressCommand;
import org.example.membership.application.port.in.FindMembershipUseCase;
import org.example.membership.domain.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;

    @GetMapping("/membership/{membershipId}")
    ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId) {

        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        Membership membership = findMembershipUseCase.findMembership(command);

        return ResponseEntity.ok(membership);
    }

    @GetMapping("/membership/address/{addressName}")
    ResponseEntity<List<Membership>> findMembershipByAddress(@PathVariable String addressName) {

        FindMembershipListByAddressCommand command = FindMembershipListByAddressCommand.builder()
                .addressName(addressName)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembershipListByAddress(command));
    }
}
