package io.github.kaisery.shi.microservice.common.constant

class SecurityConstants {
  companion object {
    val WEB_SECURITY_WHITE_LIST = arrayOf(
      "/api/**",
      "/v2/api-docs",
      "/swagger-ui.html",
      "/druid/**",
      "/h2-console/**",
      "/actuator/**"
    )
  }
}
