package io.github.kaisery.shi.microservice.common.redis

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import io.github.kaisery.shi.microservice.common.entity.uaa.Authority
import io.github.kaisery.shi.microservice.common.entity.uaa.Role
import io.github.kaisery.shi.microservice.common.entity.uaa.User
import org.codehaus.jackson.annotate.JsonCreator
import org.springframework.data.redis.serializer.SerializationException
import org.springframework.security.core.Authentication
import org.springframework.security.jackson2.CoreJackson2Module
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.TokenRequest
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy
import java.util.*

class JacksonSerializationStrategy : StandardStringSerializationStrategy() {

  private val emptyArray = ByteArray(0)

  private var objectMapper: ObjectMapper

  init {
    val hibernate5Module = Hibernate5Module()
    hibernate5Module.enable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS)

    this.objectMapper = ObjectMapper().registerModule(ParameterNamesModule())
      .registerModule(Jdk8Module())
      .registerModule(JavaTimeModule())
      .registerModule(hibernate5Module)
      .registerModule(CoreJackson2Module())
      .addMixIn(OAuth2AccessToken::class.java, OAuth2AccessTokenMixIn::class.java)
      .addMixIn(User::class.java, UserMixIn::class.java)
      .addMixIn(Role::class.java, RoleMixIn::class.java)
      .addMixIn(Authority::class.java, AuthorityMixIn::class.java)
      .addMixIn(OAuth2Request::class.java, OAuth2RequestMixIn::class.java)
      .addMixIn(HashSet::class.java, HashSetMixIn::class.java)
      .addMixIn(OAuth2Authentication::class.java, OAuth2AuthenticationMixIn::class.java)
      .addMixIn(Collections.unmodifiableMap(emptyMap<Any, Any>()).javaClass, UnmodifiableMapMixIn::class.java)
  }

  override fun <T : Any?> deserializeInternal(bytes: ByteArray?, clazz: Class<T>?): T? {
    if (bytes!!.isEmpty()) {
      return null
    }

    try {
      return this.objectMapper.readValue(bytes, 0, bytes.size, clazz) as T
    } catch (ex: Exception) {
      throw SerializationException("Could not read JSON: " + ex.message, ex)
    }
  }

  override fun serializeInternal(`object`: Any?): ByteArray {
    if (`object` == null) {
      return emptyArray
    }
    try {
      return this.objectMapper.writeValueAsBytes(`object`)
    } catch (ex: Exception) {
      throw SerializationException("Could not write JSON: " + ex.message, ex)
    }
  }

}

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, defaultImpl = DefaultOAuth2AccessToken::class)
abstract class OAuth2AccessTokenMixIn

abstract class UserMixIn(
  @JsonIgnore var password: String
)

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
abstract class RoleMixIn

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
abstract class AuthorityMixIn

class OAuth2RequestMixIn: OAuth2Request() {

  @JsonIgnore
  override fun isRefresh(): Boolean {
    return super.isRefresh()
  }

  @JsonIgnore
  override fun getRefreshTokenRequest(): TokenRequest {
    return super.getRefreshTokenRequest()
  }

  @JsonIgnore
  override fun getGrantType(): String {
    return super.getGrantType()
  }
}

abstract class HashSetMixIn

class OAuth2AuthenticationMixIn @JsonCreator constructor(
  @JsonProperty("oauth2Request") val storedRequest: OAuth2Request,
  @JsonProperty("userAuthentication") private var authentication: Authentication
) : OAuth2Authentication(storedRequest, authentication) {

  @JsonIgnore
  override fun getCredentials(): Any {
    return super.getCredentials()
  }

  @JsonIgnore
  override fun isClientOnly(): Boolean {
    return super.isClientOnly()
  }

  @JsonIgnore
  override fun getPrincipal(): Any {
    return super.getPrincipal()
  }

  @JsonIgnore
  override fun getName(): String {
    return super.getName()
  }
}

abstract class UnmodifiableMapMixIn
