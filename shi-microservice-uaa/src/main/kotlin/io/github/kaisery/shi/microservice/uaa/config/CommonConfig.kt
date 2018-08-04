package io.github.kaisery.shi.microservice.uaa.config

import io.github.kaisery.shi.microservice.common.redis.JacksonSerializationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore

@Configuration
class CommonConfig(
  val redisConnectionFactory: RedisConnectionFactory
) {

  @Bean
  fun oAuth2tokenStore(): TokenStore {
    val redisTokenStore = RedisTokenStore(redisConnectionFactory)
    redisTokenStore.setSerializationStrategy(JacksonSerializationStrategy())

    return redisTokenStore
  }

  @Bean
  fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
    val jwtAccessTokenConverter = JwtAccessTokenConverter()
    jwtAccessTokenConverter.setSigningKey("123")

    return jwtAccessTokenConverter
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }
}
