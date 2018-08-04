package io.github.kaisery.shi.microservice.common.converter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class ObjectHttpMessageConverter : HttpMessageConverter<Any> {
  companion object {
    val formHttpMessageConverter = FormHttpMessageConverter()
    val objectMapper = ObjectMapper()

    private val LINKED_MULTI_VALUE_MAP = LinkedMultiValueMap<String, String>()
    private val LINKED_MULTI_VALUE_MAP_CLASS = LINKED_MULTI_VALUE_MAP.javaClass
  }

  init {
    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  }

  override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
    return objectMapper.canSerialize(clazz) && formHttpMessageConverter.canRead(MultiValueMap::class.java, mediaType)
  }

  override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
    return objectMapper.canSerialize(clazz) && formHttpMessageConverter.canRead(MultiValueMap::class.java, mediaType)
  }

  override fun getSupportedMediaTypes(): MutableList<MediaType> {
    return formHttpMessageConverter.supportedMediaTypes
  }

  override fun write(t: Any, contentType: MediaType?, outputMessage: HttpOutputMessage) {
    formHttpMessageConverter.write(
      objectMapper.convertValue(t, LINKED_MULTI_VALUE_MAP_CLASS),
      contentType,
      outputMessage
    )
  }

  override fun read(clazz: Class<out Any>, inputMessage: HttpInputMessage): Any {
    val input = formHttpMessageConverter.read(
      LINKED_MULTI_VALUE_MAP_CLASS,
      inputMessage
    ).toSingleValueMap()

    return objectMapper.convertValue(input, clazz)
  }
}
