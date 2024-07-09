package org.example.ledgerservice.ledger.application.service

import org.example.ledgerservice.common.UseCase
import org.example.ledgerservice.ledger.application.port.`in`.DoubleLedgerEntryRecordUseCase
import org.example.ledgerservice.ledger.application.port.out.DuplicateMessageFilterPort
import org.example.ledgerservice.ledger.application.port.out.LoadAccountPort
import org.example.ledgerservice.ledger.application.port.out.LoadPaymentOrderPort
import org.example.ledgerservice.ledger.application.port.out.SaveDoubleLedgerEntryPort
import org.example.ledgerservice.ledger.domain.FinanceType
import org.example.ledgerservice.ledger.domain.Ledger
import org.example.ledgerservice.ledger.domain.LedgerEventMessage
import org.example.ledgerservice.ledger.domain.LedgerEventMessageType
import org.example.ledgerservice.ledger.domain.PaymentEventMessage

@UseCase
class DoubleLedgerEntryRecordService(
    private val duplicateMessageFilterPort: DuplicateMessageFilterPort,
    private val loadAccountPort: LoadAccountPort,
    private val loadPaymentOrderPort: LoadPaymentOrderPort,
    private val saveDoubleLedgerEntryPort: SaveDoubleLedgerEntryPort,
) : DoubleLedgerEntryRecordUseCase{

    override fun recordDoubleLedgerEntry(message: PaymentEventMessage): LedgerEventMessage {
        if (duplicateMessageFilterPort.isAlreadyProcess(message)) {
            return createLedgerEventMessage(message)
        }

        val doubleAccountsForLedger = loadAccountPort.getDoubleAccountsForLedger(FinanceType.PAYMENT_ORDER)
        val paymentOrders = loadPaymentOrderPort.getPaymentOrders(message.orderId())

        val doubleLedgerEntries = Ledger.createDoubleLedgerEntry(doubleAccountsForLedger, paymentOrders)

        saveDoubleLedgerEntryPort.save(doubleLedgerEntries)

        return createLedgerEventMessage(message)
    }

    private fun createLedgerEventMessage(message: PaymentEventMessage) =
        LedgerEventMessage(
            type = LedgerEventMessageType.SUCCESS,
            payload = mapOf(
                "orderId" to message.orderId()
            )
        )
}