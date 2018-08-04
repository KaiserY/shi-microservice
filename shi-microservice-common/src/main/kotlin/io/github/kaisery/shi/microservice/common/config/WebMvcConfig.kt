package io.github.kaisery.shi.microservice.common.config

import io.github.kaisery.shi.microservice.common.converter.ObjectHttpMessageConverter
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

  override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
    converters.add(ObjectHttpMessageConverter())
  }
}
