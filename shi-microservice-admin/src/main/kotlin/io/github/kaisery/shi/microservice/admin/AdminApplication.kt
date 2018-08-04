package io.github.kaisery.shi.microservice.admin

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
class AdminApplication

fun main(args: Array<String>) {
  runApplication<AdminApplication>(*args)
}
