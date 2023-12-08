package org.example.membership.adapter.in.web;


import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.membership.application.port.in.LoginMembershipCommand;
import org.example.membership.application.port.in.AuthMembershipUseCase;
import org.example.membership.application.port.in.RefreshTokenCommand;
import org.example.membership.application.port.in.ValidateTokenCommand;
import org.example.membership.domain.JwtToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthMembershipController {

    private final AuthMembershipUseCase authMembershipUseCase;

    @PostMapping("/membership/login")
    JwtToken loginMembership(@RequestBody LoginMembershipRequest request) {

        LoginMembershipCommand command = LoginMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .build();

        return authMembershipUseCase.loginMembership(command);
    }

    @PostMapping("/membership/refresh-token")
    JwtToken refreshToken(@RequestBody RefreshTokenRequest request) {

        RefreshTokenCommand command = RefreshTokenCommand.builder()
                .refreshToken(request.getRefreshToken())
                .build();

        return authMembershipUseCase.refreshAccessTokenByRefreshToken(command);
    }

    @PostMapping("/membership/token-validate")
    boolean validateToken(@RequestBody ValidateTokenRequest request) {

        ValidateTokenCommand command = ValidateTokenCommand.builder()
                .token(request.getToken())
                .build();

        return authMembershipUseCase.validateToken(command);
    }
}
