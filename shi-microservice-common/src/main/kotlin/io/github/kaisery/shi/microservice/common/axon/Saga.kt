package io.github.kaisery.shi.microservice.common.axon

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.saga.EndSaga
import org.axonframework.eventhandling.saga.SagaEventHandler
import org.axonframework.eventhandling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import java.util.*

@Saga
class OrderManagment {
  lateinit var orderId: String
  lateinit var shipmentId: String
  lateinit var paymentId: String

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  fun handle(event: OrderCreated, commandGateway: CommandGateway) {
    this.orderId = event.orderId
    this.shipmentId = UUID.randomUUID().toString()
    this.paymentId = UUID.randomUUID().toString()
    commandGateway.send<String>(CreateShipment(this.shipmentId, this.orderId))
    commandGateway.send<String>(CreatePayment(this.paymentId, this.orderId))
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  fun handle(event: OrderCanceled, commandGateway: CommandGateway) {
    commandGateway.send<String>(CancelShipment(this.shipmentId))
    commandGateway.send<String>(CancelPayment(this.paymentId))
  }
}
