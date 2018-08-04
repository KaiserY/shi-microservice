package io.github.kaisery.shi.microservice.demo.controller

import io.github.kaisery.shi.microservice.common.client.UAAClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(
  private val uaaClient: UAAClient
) {

  @RequestMapping(path = ["/"], method = [RequestMethod.GET])
  fun hello(): String {
    return "hello"
  }

  @RequestMapping(path = ["/a"], method = [RequestMethod.GET])
  fun a(): String {
    return uaaClient.me()
  }

  @RequestMapping(path = ["/b"], method = [RequestMethod.GET])
  fun b(): String {
    return uaaClient.me()
  }
}
