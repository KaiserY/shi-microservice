package io.github.kaisery.shi.microservice.common.spring

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.kaisery.shi.microservice.common.constant.OAuth2Scope
import io.github.kaisery.shi.microservice.common.dto.ResponseDto
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice(basePackageClasses = [
  io.github.kaisery.shi.microservice.ScanMarker::class,
  org.springframework.security.oauth2.provider.endpoint.TokenEndpoint::class
])
class UnifiedResponseBodyAdvice(val objectMapper: ObjectMapper) : ResponseBodyAdvice<Any> {

  override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean = true

  override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType, selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest, response: ServerHttpResponse): Any? {
    if (body is ResponseDto<*>) return body

    if (returnType.executable.declaringClass == TokenEndpoint::class.java && body is OAuth2AccessToken) {

      for (scope in body.scope) {
        if (OAuth2Scope.STANDARD == scope) {
          return body
        }
      }
    }

    val httpServletResponse = (response as ServletServerHttpResponse).servletResponse

    val code = Integer.toString(httpServletResponse.status)
    val status = HttpStatus.resolve(httpServletResponse.status)

    var msg = "error"

    if (status != null) {
      msg = status.reasonPhrase
    }

    val responseDto = ResponseDto(
      code,
      msg,
      System.currentTimeMillis(),
      body
    )

    return if (selectedConverterType == StringHttpMessageConverter::class.java) {
      try {
        objectMapper.writeValueAsString(responseDto)
      } catch (ex: Exception) {
        body
      }
    } else {
      responseDto
    }
  }
}
