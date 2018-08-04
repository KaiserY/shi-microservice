package io.github.kaisery.shi.microservice.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.reflect.TypeToken
import feign.FeignException
import feign.RequestTemplate
import feign.Response
import feign.codec.Decoder
import feign.codec.Encoder
import feign.codec.ErrorDecoder
import feign.form.FormEncoder
import feign.optionals.OptionalDecoder
import io.github.kaisery.shi.microservice.common.client.ScanMarker
import io.github.kaisery.shi.microservice.common.converter.ObjectHttpMessageConverter
import io.github.kaisery.shi.microservice.common.dto.ResponseDto
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.stereotype.Component
import java.lang.Exception
import java.lang.reflect.Type

@Configuration
@EnableOAuth2Client
@EnableFeignClients(
  basePackageClasses = arrayOf(ScanMarker::class)
)
@Profile("microservice-oauth-client")
class ClientCredentialsFeignClientConfig(
  val messageConverters: ObjectFactory<HttpMessageConverters>,
  val objectMapper: ObjectMapper
) {

  @Bean
  fun encoder(): Encoder {
    val httpMessageConvertersList = ArrayList(messageConverters.getObject().converters)

    httpMessageConvertersList.add(ObjectHttpMessageConverter())

    val messageConvertersObjectFactory = ObjectFactory { HttpMessageConverters(false, httpMessageConvertersList) }

    return FormEncoder(SpringEncoder(messageConvertersObjectFactory))
  }

  @Bean
  fun decoder(): Decoder {
    return OptionalDecoder(OAuth2FeignDecoder(SpringDecoder(messageConverters)))
  }
}

@Component
@Profile("microservice-oauth-client")
class TokenRelayInterceptor(
  val oAuth2ClientContext: OAuth2ClientContext,
  val context: OAuth2ClientContext,
  val details: OAuth2ProtectedResourceDetails
) : OAuth2FeignRequestInterceptor(context, details) {

  override fun apply(template: RequestTemplate?) {
    val authentication = SecurityContextHolder.getContext().authentication

    if (authentication is OAuth2Authentication) {
      val token = (authentication.getDetails() as OAuth2AuthenticationDetails).tokenValue
      oAuth2ClientContext.accessToken = DefaultOAuth2AccessToken(token)
    } else {
      oAuth2ClientContext.accessToken = null
    }

    super.apply(template)
  }
}

@Component
@Profile("microservice-oauth-client")
class OAuth2FeignErrorDecoder(
  val oauth2ClientContext: OAuth2ClientContext
) : ErrorDecoder {

  override fun decode(methodKey: String?, response: Response?): Exception {
    if (HttpStatus.UNAUTHORIZED.value() == response!!.status()) {

      oauth2ClientContext.accessToken = null
    }

    return FeignException.errorStatus(methodKey, response);
  }
}

class OAuth2FeignDecoder(private val decoder: Decoder) : ResponseEntityDecoder(decoder) {
  companion object {
    val objectMapper = ObjectMapper()

    val wrapperType: Type? = object : TypeToken<ResponseDto<*>>() {}.type
  }

  override fun decode(response: Response?, type: Type?): Any {
    return objectMapper.convertValue(
      (super.decode(response, wrapperType) as ResponseDto<*>).data,
      objectMapper.typeFactory.constructType(type)
    )
  }
}
