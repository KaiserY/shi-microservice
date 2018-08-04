package io.github.kaisery.shi.microservice.uaa.config

import io.github.kaisery.shi.microservice.common.constant.OAuth2Scope
import io.github.kaisery.shi.microservice.uaa.oauth.CustomTokenGranter
import io.github.kaisery.shi.microservice.uaa.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.CompositeTokenGranter
import org.springframework.security.oauth2.provider.TokenGranter
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationServerConfig(
  val authenticationManager: AuthenticationManager,
  val userService: UserService,
  val passwordEncoder: PasswordEncoder,
  val tokenStore: TokenStore,
  val jwtAccessTokenConverter: JwtAccessTokenConverter
) : AuthorizationServerConfigurerAdapter() {

  override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
    endpoints!!.authenticationManager(authenticationManager)
      .tokenStore(tokenStore)
      .accessTokenConverter(jwtAccessTokenConverter)
      .tokenGranter(tokenGranter(endpoints))
  }

  override fun configure(clients: ClientDetailsServiceConfigurer?) {
    clients!!.inMemory()
      .withClient("server")
      .secret(passwordEncoder.encode("server"))
      .authorizedGrantTypes("client_credentials", "refresh_token")
      .scopes(OAuth2Scope.STANDARD)
      .and()
      .withClient("custom")
      .secret(passwordEncoder.encode("custom"))
      .authorizedGrantTypes("custom", "refresh_token")
      .scopes(OAuth2Scope.CUSTOM)
  }

  override fun configure(security: AuthorizationServerSecurityConfigurer?) {
    security!!.allowFormAuthenticationForClients()
  }

  fun tokenGranter(endpoints: AuthorizationServerEndpointsConfigurer): TokenGranter {
    val tokenGranters = arrayListOf(endpoints.tokenGranter)

    tokenGranters.add(CustomTokenGranter(
      userService,
      endpoints.tokenServices,
      endpoints.clientDetailsService,
      endpoints.oAuth2RequestFactory,
      "custom"))

    return CompositeTokenGranter(tokenGranters)
  }
}
