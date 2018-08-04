package io.github.kaisery.shi.microservice.uaa.oauth

import io.github.kaisery.shi.microservice.uaa.service.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.provider.*
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices

class CustomTokenGranter(
  val userService: UserService,
  tokenServices: AuthorizationServerTokenServices?,
  clientDetailsService: ClientDetailsService?,
  requestFactory: OAuth2RequestFactory?,
  grantType: String?
) : AbstractTokenGranter(tokenServices, clientDetailsService, requestFactory, grantType) {

  override fun getOAuth2Authentication(client: ClientDetails?, tokenRequest: TokenRequest?): OAuth2Authentication {
    val params = tokenRequest!!.requestParameters;
    val username = params.get("username") ?: "";

    val user = userService.loadUserByUsername(username)

    val token = UsernamePasswordAuthenticationToken(user, null, user.authorities)

    val oauth2Token = OAuth2Authentication(
      tokenRequest.createOAuth2Request(client),
      token
    )

    return oauth2Token
  }
}
