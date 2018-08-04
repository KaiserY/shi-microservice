package io.github.kaisery.shi.microservice.common.constant

class OAuth2GrantType {
  companion object {
    const val AUTHORIZATION_CODE = "authorization_code"
    const val PASSWORD = "password"
    const val CLIENT_CREDENTIALS = "client_credentials"
    const val REFRESH_TOKEN = "refresh_token"
    const val IMPLICIT = "implicit"
    const val CUSTOM = "custom"
  }
}

class OAuth2Scope {
  companion object {
    const val STANDARD = "standard"
    const val CUSTOM = "custom"
  }
}
