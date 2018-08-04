package io.github.kaisery.shi.microservice.common.axon

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.axonframework.serialization.Revision

data class CreateOrder(@TargetAggregateIdentifier val orderId: String)
@Revision("1.0.0")
data class OrderCreated(val orderId: String)

data class UpdateOrder(@TargetAggregateIdentifier val orderId: String)
@Revision("1.0.0")
data class OrderUpdated(val orderId: String)

data class CancelOrder(@TargetAggregateIdentifier val orderId: String)
@Revision("1.0.0")
data class OrderCanceled(val orderId: String)

@Revision("1.0.0")
data class OrderStopped(val orderId: String, val action: String)


data class CreateShipment(@TargetAggregateIdentifier val shipmentId: String, val orderId: String)
@Revision("1.0.0")
data class ShipmentCreated(val shipmentId: String, val orderId: String)

data class CancelShipment(@TargetAggregateIdentifier val shipmentId: String)
@Revision("1.0.0")
data class ShipmentCanceled(val shipmentId: String)


data class CreatePayment(@TargetAggregateIdentifier val paymentId: String, val orderId: String)
@Revision("1.0.0")
data class PaymentCreated(val paymentId: String, val orderId: String)

data class CancelPayment(@TargetAggregateIdentifier val paymentId: String)
@Revision("1.0.0")
data class PaymentCanceled(val paymentId: String)
