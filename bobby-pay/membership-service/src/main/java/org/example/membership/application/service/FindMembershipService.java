package org.example.membership.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.membership.adapter.out.persistence.MembershipJpaEntity;
import org.example.membership.adapter.out.persistence.MembershipMapper;
import org.example.membership.application.port.in.FindMembershipCommand;
import org.example.membership.application.port.in.FindMembershipListByAddressCommand;
import org.example.membership.application.port.in.FindMembershipUseCase;
import org.example.membership.application.port.out.FindMembershipPort;
import org.example.membership.domain.Membership;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

	private final FindMembershipPort findMembershipPort;
	private final MembershipMapper mapper;

	@Override
	public Membership findMembership(FindMembershipCommand command) {
		MembershipJpaEntity entity = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
		return mapper.mapToDomainEntity(entity);
	}

	@Override
	public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {
		return findMembershipPort.findMembershipListByAddress(new Membership.MembershipAddress(command.getAddressName()))
				.stream()
				.map(mapper::mapToDomainEntity)
				.collect(Collectors.toList());
	}

}



