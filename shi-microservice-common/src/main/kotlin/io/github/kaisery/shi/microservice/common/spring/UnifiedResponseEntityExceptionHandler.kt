package io.github.kaisery.shi.microservice.common.spring

import io.github.kaisery.shi.microservice.common.dto.ResponseDto
import io.github.kaisery.shi.microservice.common.exception.CommonBizException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class UnifiedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(value = [CommonBizException::class])
  fun handleCommonBizException(ex: CommonBizException): ResponseEntity<ResponseDto<*>> {
    return ResponseEntity(
      ResponseDto(
        ex.errorCode,
        ex.message ?: "unknown error",
        System.currentTimeMillis(),
        "Common Biz Exception"
      ),
      HttpStatus.OK
    )
  }
}
