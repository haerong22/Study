
package org.example.membership.application.port.out;

import org.example.membership.adapter.out.persistence.MembershipJpaEntity;
import org.example.membership.domain.Membership;

import java.util.List;

public interface FindMembershipPort {

	MembershipJpaEntity findMembership(
			Membership.MembershipId membershipId
	);

	List<MembershipJpaEntity> findMembershipListByAddress(
			Membership.MembershipAddress membershipAddress
	);
}