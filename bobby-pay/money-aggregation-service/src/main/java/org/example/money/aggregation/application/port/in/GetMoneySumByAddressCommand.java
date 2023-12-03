package org.example.money.aggregation.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class GetMoneySumByAddressCommand extends SelfValidating<GetMoneySumByAddressCommand> {

    @NotEmpty
    private final String address;

    public GetMoneySumByAddressCommand(String address) {
        this.address = address;

        this.validateSelf();
    }
}
