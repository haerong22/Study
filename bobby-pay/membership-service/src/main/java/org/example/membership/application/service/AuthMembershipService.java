package org.example.membership.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.membership.adapter.out.persistence.MembershipJpaEntity;
import org.example.membership.application.port.in.AuthMembershipUseCase;
import org.example.membership.application.port.in.LoginMembershipCommand;
import org.example.membership.application.port.in.RefreshTokenCommand;
import org.example.membership.application.port.out.AuthMembershipPort;
import org.example.membership.application.port.out.FindMembershipPort;
import org.example.membership.application.port.out.ModifyMembershipPort;
import org.example.membership.domain.JwtToken;
import org.example.membership.domain.Membership;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AuthMembershipService implements AuthMembershipUseCase {

	private final AuthMembershipPort authMembershipPort;
	private final FindMembershipPort findMembershipPort;
	private final ModifyMembershipPort modifyMembershipPort;

	@Override
	public JwtToken loginMembership(LoginMembershipCommand command) {

		Long membershipId = command.getMembershipId();

		MembershipJpaEntity jpaEntity = findMembershipPort.findMembership(
				new Membership.MembershipId(String.valueOf(membershipId))
		);

		if (jpaEntity.isValid()) {
			String accessToken = authMembershipPort.generateAccessToken(membershipId);
			String refreshToken = authMembershipPort.generateRefreshToken(membershipId);

			modifyMembershipPort.modifyMembership(
					new Membership.MembershipId(String.valueOf(membershipId)),
					new Membership.MembershipName(jpaEntity.getName()),
					new Membership.MembershipEmail(jpaEntity.getEmail()),
					new Membership.MembershipAddress(jpaEntity.getAddress()),
					new Membership.MembershipIsValid(jpaEntity.isValid()),
					new Membership.MembershipIsCorp(jpaEntity.isCorp()),
					new Membership.RefreshToken(refreshToken)
			);

			return JwtToken.generateJwtToken(
					new JwtToken.MembershipId(membershipId),
					new JwtToken.AccessToken(accessToken),
					new JwtToken.RefreshToken(refreshToken)
			);
		}


		return null;
	}

	@Override
	public JwtToken refreshAccessTokenByRefreshToken(RefreshTokenCommand command) {

		String refreshToken = command.getRefreshToken();

		boolean isValid = authMembershipPort.validateToken(refreshToken);

		if (isValid) {
			Long membershipId = authMembershipPort.parseMembershipIdFromToken(refreshToken);

			MembershipJpaEntity jpaEntity = findMembershipPort.findMembership(new Membership.MembershipId(String.valueOf(membershipId)));

			if (jpaEntity.isValid() && jpaEntity.getRefreshToken().equals(refreshToken)) {
				String newAccessToken = authMembershipPort.generateAccessToken(membershipId);

				return JwtToken.generateJwtToken(
						new JwtToken.MembershipId(membershipId),
						new JwtToken.AccessToken(newAccessToken),
						new JwtToken.RefreshToken(refreshToken)
				);
			}
		}

		return null;
	}
}



