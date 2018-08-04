package io.github.kaisery.shi.microservice.common.axon

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Order {

  @AggregateIdentifier
  lateinit var id: String
  lateinit var status: String

  constructor()

  @CommandHandler
  constructor(cmd: CreateOrder) {
    AggregateLifecycle.apply(OrderCreated(cmd.orderId))
  }

  @EventSourcingHandler
  fun on(event: OrderCreated) {
    println("Aggregate $event - $this")
    id = event.orderId
    status = "CREATED"
  }

  @CommandHandler
  fun updateOrder(cmd: UpdateOrder) {
    AggregateLifecycle.apply(OrderUpdated(cmd.orderId))
  }

  @EventSourcingHandler
  fun on(event: OrderUpdated) {
    status = "UPDATED"
    println("Aggregate $event - $this")
  }

  @CommandHandler
  fun cancelOrder(cmd: CancelOrder) {
    AggregateLifecycle.apply(OrderCanceled(cmd.orderId))
  }

  @EventSourcingHandler
  fun on(event: OrderCanceled) {
    status = "CANCELED"
    println("Aggregate $event - $this")
  }
}

@Aggregate
class Shipment {

  @AggregateIdentifier
  lateinit var id: String

  constructor()

  @CommandHandler
  constructor(cmd: CreateShipment) {
    AggregateLifecycle.apply(ShipmentCreated(cmd.shipmentId, cmd.orderId))
  }

  @EventSourcingHandler
  fun on(event: ShipmentCreated) {
    println("Aggregate $event")
    this.id = event.shipmentId
  }

  @CommandHandler
  fun cancelOrder(cmd: CancelShipment) {
    AggregateLifecycle.apply(ShipmentCanceled(cmd.shipmentId))
  }

  @EventSourcingHandler
  fun on(event: ShipmentCanceled) {
    println("Aggregate $event")
  }
}

@Aggregate
class Payment {

  @AggregateIdentifier
  lateinit var id: String

  constructor()

  @CommandHandler
  constructor(cmd: CreatePayment) {
    AggregateLifecycle.apply(PaymentCreated(cmd.paymentId, cmd.orderId))
  }

  @EventSourcingHandler
  fun on(event: PaymentCreated) {
    println("Aggregate $event")
    this.id = event.paymentId
  }

  @CommandHandler
  fun cancelPayment(cmd: CancelPayment) {
    AggregateLifecycle.apply(PaymentCanceled(cmd.paymentId))
  }

  @EventSourcingHandler
  fun on(event: PaymentCanceled) {
    println("Aggregate $event")
  }
}
