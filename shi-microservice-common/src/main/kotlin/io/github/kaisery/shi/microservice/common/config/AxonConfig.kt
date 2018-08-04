package io.github.kaisery.shi.microservice.common.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan(
  "org.axonframework.eventsourcing.eventstore.jpa",
  "org.axonframework.eventhandling.saga.repository.jpa",
  "org.axonframework.eventhandling.tokenstore.jpa")
class AxonConfig {
}
