package org.example.money.query.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryMoneySumByRegionQuery extends SelfValidating<QueryMoneySumByRegionQuery> {
    private final String address;
}