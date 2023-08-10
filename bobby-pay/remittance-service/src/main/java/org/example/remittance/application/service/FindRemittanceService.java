package org.example.remittance.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.remittance.adapter.out.persistence.RemittanceRequestMapper;
import org.example.remittance.application.port.in.FindRemittanceCommand;
import org.example.remittance.application.port.in.FindRemittanceUseCase;
import org.example.remittance.application.port.out.FindRemittancePort;
import org.example.remittance.domain.RemittanceRequest;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        //
        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}