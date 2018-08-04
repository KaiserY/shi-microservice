package io.github.kaisery.shi.microservice.common.dto

data class ResponseDto<T>(
  var code: String,
  var msg: String,
  var timestamp: Long,
  var data: T?
)
