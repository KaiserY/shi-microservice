package io.github.kaisery.shi.microservice.uaa.controller

import io.github.kaisery.shi.microservice.uaa.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {

  @Autowired
  lateinit var userService: UserService

  @GetMapping(path = ["/me"], produces = ["application/json"])
  fun me(): String {
    return SecurityContextHolder.getContext().authentication.toString()
  }
}
