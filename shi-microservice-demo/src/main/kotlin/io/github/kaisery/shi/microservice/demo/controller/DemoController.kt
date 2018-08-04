package io.github.kaisery.shi.microservice.demo.controller

import io.github.kaisery.shi.microservice.common.client.UAAClient
import io.github.kaisery.shi.microservice.common.dto.UserResDto
import io.github.kaisery.shi.microservice.common.dto.UserSignupDto
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
  fun a(): UserResDto {
    return uaaClient.me()
  }

  @RequestMapping(path = ["/b"], method = [RequestMethod.GET])
  fun b(): UserSignupDto {
    return uaaClient.test(UserSignupDto("aa", "aa"))
  }
}
