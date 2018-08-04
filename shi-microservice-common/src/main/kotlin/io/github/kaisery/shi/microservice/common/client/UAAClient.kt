package io.github.kaisery.shi.microservice.common.client

import io.github.kaisery.shi.microservice.common.dto.UserResDto
import io.github.kaisery.shi.microservice.common.dto.UserSignupDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("shi-microservice-uaa")
interface UAAClient {

  @RequestMapping(
    method = [RequestMethod.GET],
    value = ["/api/user/me"],
    produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
  )
  fun me(): UserResDto

  @RequestMapping(
    method = [RequestMethod.GET],
    value = ["/api/auth/test"],
    produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
  )
  fun test(@RequestBody userSignupDto: UserSignupDto): UserSignupDto
}
