package io.github.kaisery.shi.microservice.zipkin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import zipkin.server.EnableZipkinServer

@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
class ZipkinApplication {
  companion object {
    init {
      System.setProperty(
        "java.util.logging.manager",
        "org.apache.logging.log4j.jul.LogManager"
      )
    }
  }
}

fun main(args: Array<String>) {
  runApplication<ZipkinApplication>(*args)
}
