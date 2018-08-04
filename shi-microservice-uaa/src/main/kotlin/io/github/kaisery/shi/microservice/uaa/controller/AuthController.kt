package io.github.kaisery.shi.microservice.uaa.controller

import io.github.kaisery.shi.microservice.common.dto.UserResDto
import io.github.kaisery.shi.microservice.common.dto.UserSignupDto
import io.github.kaisery.shi.microservice.common.entity.uaa.User
import io.github.kaisery.shi.microservice.uaa.service.UserService
import io.swagger.annotations.ApiOperation
import org.modelmapper.ModelMapper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
  val modelMapper: ModelMapper,
  val userService: UserService
) {

  @ApiOperation(value = "注册")
  @PostMapping(path = arrayOf("/signup"), produces = arrayOf("application/json"))
  fun signup(@RequestBody userSignupDto: UserSignupDto): UserResDto {
    return userService.signup(modelMapper.map(userSignupDto, User::class.java))
  }
}
