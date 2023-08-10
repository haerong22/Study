package org.example.remittance.application.port.out;

import org.example.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import org.example.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

    RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);
    boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}