package org.example.ledgerservice.ledger.application.port.out

import org.example.ledgerservice.ledger.domain.PaymentEventMessage

interface DuplicateMessageFilterPort {

    fun isAlreadyProcess(message: PaymentEventMessage): Boolean
}