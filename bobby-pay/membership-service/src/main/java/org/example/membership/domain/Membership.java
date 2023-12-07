package org.example.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {

    private final String membershipId;
    private final String name;
    private final String email;
    private final String address;
    private final boolean isValid;
    private final boolean isCorp;
    private final String refreshToken;

    public static Membership generateMember(
            MembershipId membershipId,
            MembershipName membershipName,
            MembershipEmail membershipEmail,
            MembershipAddress membershipAddress,
            MembershipIsValid membershipIsValid,
            MembershipIsCorp membershipIsCorp,
            RefreshToken refreshToken
    ) {
        return new Membership(
                membershipId.membershipId,
                membershipName.membershipName,
                membershipEmail.membershipEmail,
                membershipAddress.membershipAddress,
                membershipIsValid.membershipIsValid,
                membershipIsCorp.membershipIsCorp,
                refreshToken.refreshToken
        );
    }

    @Value
    public static class MembershipId {

        String membershipId;

        public MembershipId(String value) {
            this.membershipId = value;
        }
    }

    @Value
    public static class MembershipName {

        String membershipName;

        public MembershipName(String value) {
            this.membershipName = value;
        }
    }

    @Value
    public static class MembershipEmail {

        String membershipEmail;

        public MembershipEmail(String value) {
            this.membershipEmail = value;
        }
    }

    @Value
    public static class MembershipAddress {

        String membershipAddress;

        public MembershipAddress(String value) {
            this.membershipAddress = value;
        }
    }

    @Value
    public static class MembershipIsValid {

        boolean membershipIsValid;

        public MembershipIsValid(boolean value) {
            this.membershipIsValid = value;
        }
    }

    @Value
    public static class MembershipIsCorp {

        boolean membershipIsCorp;

        public MembershipIsCorp(boolean value) {
            this.membershipIsCorp = value;
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
