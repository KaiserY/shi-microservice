package io.github.kaisery.shi.microservice.uaa.controller

import io.github.kaisery.shi.microservice.common.dto.UserResDto
import io.github.kaisery.shi.microservice.common.entity.uaa.User
import org.modelmapper.ModelMapper
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
  val modelMapper: ModelMapper
) {

  @GetMapping(path = ["/me"])
  @PreAuthorize("#oauth2.isUser()")
  fun me(): UserResDto {
    return modelMapper.map(
      SecurityContextHolder.getContext().authentication.principal as User,
      UserResDto::class.java
    )
  }
}
