package io.github.kaisery.shi.microservice.common.dto

import io.github.kaisery.shi.microservice.common.annotation.NoArg

@NoArg
data class UserResDto(
  var username: String,
  var state: Int = 0
)

@NoArg
data class UserSignupDto(
  var username: String,
  var password: String
)
