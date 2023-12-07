package org.example.membership.application.port.in;

import org.example.membership.domain.JwtToken;

public interface AuthMembershipUseCase {

    JwtToken loginMembership(LoginMembershipCommand command);
}