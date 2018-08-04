package io.github.kaisery.shi.microservice.uaa.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableResourceServer
class OAuth2ResourceServerConfig(
  val tokenStore: TokenStore
) : ResourceServerConfigurerAdapter() {

  override fun configure(resources: ResourceServerSecurityConfigurer?) {
    resources!!.resourceId("uaa").tokenStore(tokenStore)
  }

  override fun configure(http: HttpSecurity?) {
    http!!
      .authorizeRequests()
      .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
      .antMatchers("/**").permitAll()
      .antMatchers("/api/user").authenticated()
      .and()
      .httpBasic().disable()
      .headers().frameOptions().disable()
  }
}
