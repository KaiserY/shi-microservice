package io.github.kaisery.shi.microservice.common.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("shi-microservice-uaa", fallback = UAAFeignClientFallback::class)
interface UAAClient {

  @RequestMapping(
    method = [RequestMethod.GET],
    value = ["/api/user/me"],
    consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE]
  )
  fun me(): String
}

@Component
class UAAFeignClientFallback : UAAClient {

  override fun me(): String {
    return "me fallback"
  }
}
