package org.example.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {

    private final Long membershipId;
    private final String accessToken;
    private final String refreshToken;

    public static JwtToken generateJwtToken(
            MembershipId membershipId,
            AccessToken accessToken,
            RefreshToken refreshToken
    ) {
        return new JwtToken(
                membershipId.membershipId,
                accessToken.accessToken,
                refreshToken.refreshToken
        );
    }

    @Value
    public static class MembershipId {

        Long membershipId;

        public MembershipId(Long value) {
            this.membershipId = value;
        }
    }

    @Value
    public static class AccessToken {

        String accessToken;

        public AccessToken(String value) {
            this.accessToken = value;
        }
    }

    @Value
    public static class RefreshToken {

        String refreshToken;

        public RefreshToken(String value) {
            this.refreshToken = value;
        }
    }
}
