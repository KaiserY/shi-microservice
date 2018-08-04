package io.github.kaisery.shi.microservice.uaa.config

import io.github.kaisery.shi.microservice.common.constant.SecurityConstants
import io.github.kaisery.shi.microservice.uaa.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
  val userService: UserService
) : WebSecurityConfigurerAdapter() {

  @Bean
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }

  override fun configure(http: HttpSecurity?) {
    http!!
      .authorizeRequests()
      .antMatchers(*SecurityConstants.WEB_SECURITY_WHITE_LIST).permitAll()
      .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
      .anyRequest().authenticated()
  }

  override fun configure(auth: AuthenticationManagerBuilder?) {
    auth!!.userDetailsService(userService)
  }
}
