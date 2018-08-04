package io.github.kaisery.shi.microservice.common.exception

abstract class AbstractBizException(
  val errorCode: String,
  message: String
) : RuntimeException(message) {
}

class CommonBizException(
  errorCode: String,
  message: String
) : AbstractBizException(errorCode, message)
