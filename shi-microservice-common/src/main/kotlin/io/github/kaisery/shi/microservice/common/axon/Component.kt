package io.github.kaisery.shi.microservice.common.axon

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class EventLogger {

  @EventHandler
  fun on(event: Any) {
    println("Event $event")
  }
}

//@Component
class Simulator(val commandGateway: CommandGateway) : CommandLineRunner {
  override fun run(vararg args: String) {
    val orderId = UUID.randomUUID().toString()

    println("-----> Create Order")
    commandGateway.send<String>(CreateOrder(orderId))
    println("-----> Update Order")
    commandGateway.send<String>(UpdateOrder(orderId))
    println("-----> Cancel Order")
    commandGateway.send<String>(CancelOrder(orderId))
    println("-----> Update Order")
    commandGateway.send<String>(UpdateOrder(orderId))
    println("-----> Cancel Order")
    commandGateway.send<String>(CancelOrder(orderId))
  }
}
