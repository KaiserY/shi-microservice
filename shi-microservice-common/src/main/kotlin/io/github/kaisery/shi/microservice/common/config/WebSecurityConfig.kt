package io.github.kaisery.shi.microservice.common.config

import io.github.kaisery.shi.microservice.common.constant.SecurityConstants
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("microservice-oauth-client")
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.authorizeRequests()
      .antMatchers(*SecurityConstants.WEB_SECURITY_WHITE_LIST).permitAll()
      .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
      .anyRequest().authenticated()
  }
}
