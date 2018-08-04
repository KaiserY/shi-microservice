package io.github.kaisery.shi.microservice.uaa.controller

import io.github.kaisery.shi.microservice.common.dto.UserResDto
import io.github.kaisery.shi.microservice.common.dto.UserSignupDto
import io.github.kaisery.shi.microservice.common.entity.uaa.User
import io.github.kaisery.shi.microservice.common.exception.CommonBizException
import io.github.kaisery.shi.microservice.uaa.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
  val modelMapper: ModelMapper,
  val userService: UserService
) {

  @PostMapping(path = ["/signup"])
  fun signup(@RequestBody userSignupDto: UserSignupDto): UserResDto {
    return userService.signup(modelMapper.map(userSignupDto, User::class.java))
  }

  @PostMapping(path = ["/test"])
  fun test(@RequestBody userSignupDto: UserSignupDto): UserSignupDto {
    return userSignupDto
  }

  @GetMapping(path = ["/ex"])
  fun ex(): UserResDto {
    throw CommonBizException("1", "ex")
  }
}
