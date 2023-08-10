package org.example.remittance.application.port.out;

import org.example.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import org.example.remittance.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}