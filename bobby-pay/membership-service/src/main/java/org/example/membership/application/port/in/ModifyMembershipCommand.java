package org.example.membership.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {

    @NotEmpty
    private final String membershipId;

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String email;

    @NotEmpty
    private final String address;

    @AssertTrue
    private final boolean isValid;

    private final boolean isCorp;

    private final String refreshToken;

    public ModifyMembershipCommand(String membershipId, String name, String email, String address, boolean isValid, boolean isCorp, String refreshToken) {
        this.membershipId = membershipId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;
        this.refreshToken = refreshToken;

        this.validateSelf();
    }
}
