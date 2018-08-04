package io.github.kaisery.shi.microservice.uaa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackageClasses = [
  io.github.kaisery.shi.microservice.ScanMarker::class
])
@EnableDiscoveryClient
@EntityScan(basePackageClasses = [
  io.github.kaisery.shi.microservice.common.entity.common.ScanMarker::class,
  io.github.kaisery.shi.microservice.common.entity.uaa.ScanMarker::class,
  io.github.kaisery.shi.microservice.uaa.entity.ScanMarker::class
])
@EnableJpaRepositories(basePackageClasses = [
  io.github.kaisery.shi.microservice.common.repository.ScanMarker::class,
  io.github.kaisery.shi.microservice.uaa.repository.ScanMarker::class
])
class UAAApplication

fun main(args: Array<String>) {
  runApplication<UAAApplication>(*args)
}
