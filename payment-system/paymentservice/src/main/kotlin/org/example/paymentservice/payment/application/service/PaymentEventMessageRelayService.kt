package org.example.paymentservice.payment.application.service

import org.example.paymentservice.common.Log
import org.example.paymentservice.common.UseCase
import org.example.paymentservice.payment.application.port.`in`.PaymentEventMessageRelayUseCase
import org.example.paymentservice.payment.application.port.out.DispatchEventMessagePort
import org.example.paymentservice.payment.application.port.out.LoadPendingPaymentEventMessagePort
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import reactor.core.scheduler.Schedulers
import java.util.concurrent.TimeUnit

@UseCase
@Profile("dev")
class PaymentEventMessageRelayService (
  private val loadPendingPaymentEventMessagePort: LoadPendingPaymentEventMessagePort,
  private val dispatchEventMessagePort: DispatchEventMessagePort
) : PaymentEventMessageRelayUseCase {

  private val scheduler = Schedulers.newSingle("message-relay")

  @Scheduled(fixedDelay = 180, initialDelay = 180, timeUnit = TimeUnit.SECONDS)
  override fun relay() {
    loadPendingPaymentEventMessagePort.getPendingPaymentEventMessage()
      .map { dispatchEventMessagePort.dispatch(it) }
      .onErrorContinue { err, _ ->  Log.error("messageRelay", err.message ?: "failed to relay message.", err)}
      .subscribeOn(scheduler)
      .subscribe()
  }
}