package org.example.money.query.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.example.common.UseCase;
import org.example.money.query.adapter.axon.QueryMoneySumByAddress;
import org.example.money.query.application.port.in.QueryMoneySumByRegionQuery;
import org.example.money.query.application.port.in.QueryMoneySumByRegionUseCase;
import org.example.money.query.domain.MoneySumByRegion;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@UseCase
@RequiredArgsConstructor
@Transactional
public class QueryMoneySumByRegionService implements QueryMoneySumByRegionUseCase {
    private final QueryGateway queryGateway;
    @Override
    public MoneySumByRegion queryMoneySumByRegion(QueryMoneySumByRegionQuery query) {
        try {
            return queryGateway.query(new QueryMoneySumByAddress(query.getAddress()), MoneySumByRegion.class).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}