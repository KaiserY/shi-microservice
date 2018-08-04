package io.github.kaisery.shi.microservice.common.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.OAuthBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger.web.SecurityConfigurationBuilder
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@ConfigurationProperties("microservice.swagger")
class SwaggerProperties {
  lateinit var host: String
  lateinit var basePath: String
  lateinit var basePackage: String
  lateinit var oauth2ClientId: String
  lateinit var oauth2ClientSecret: String
  lateinit var oauth2Scope: String
  lateinit var oauth2ScopeDescription: String
  lateinit var oauth2Schema: String
  lateinit var tokenUrl: String

  val apiInfo = ApiInfo()

  class ApiInfo {
    lateinit var title: String
    lateinit var description: String
  }
}

@Configuration
@ConditionalOnProperty(name = arrayOf("enabled"), prefix = "microservice.swagger")
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration::class)
class SwaggerConfig(private val swaggerProperties: SwaggerProperties) {

  @Bean
  fun oauthApi(): Docket {
    return Docket(DocumentationType.SWAGGER_2).select()
      .apis(RequestHandlerSelectors.basePackage(swaggerProperties.basePackage))
      .build()
      .host(swaggerProperties.host)
      .securitySchemes(Collections.singletonList(securityScheme()))
      .securityContexts(Collections.singletonList(securityContext()))
      .pathMapping(swaggerProperties.basePath)
      .apiInfo(apiInfo());
  }

  @Bean
  fun uiConfiguration(): UiConfiguration {
    return UiConfigurationBuilder.builder().build()
  }

  @Bean
  fun securityConfiguration(): SecurityConfiguration {
    return SecurityConfigurationBuilder
      .builder()
      .clientId(swaggerProperties.oauth2ClientId)
      .clientSecret(swaggerProperties.oauth2ClientSecret)
      .build()
  }

  @Bean
  fun securityScheme(): SecurityScheme {
    return OAuthBuilder()
      .name(swaggerProperties.oauth2Schema)
      .grantTypes(grantTypes())
      .scopes(scopes())
      .build()
  }

  private fun securityContext(): SecurityContext {
    return SecurityContext
      .builder()
      .securityReferences(defaultAuth())
      .forPaths(PathSelectors.ant("/api/**"))
      .build()
  }

  private fun defaultAuth(): List<SecurityReference> {
    return listOf(SecurityReference(
      swaggerProperties.oauth2Schema,
      scopes().toTypedArray()))
  }

  private fun scopes(): List<AuthorizationScope> {
    return Arrays.asList(
      AuthorizationScope(
        swaggerProperties.oauth2Scope,
        swaggerProperties.oauth2ScopeDescription
      ))
  }

  private fun grantTypes(): List<GrantType> {
    val grantType = ResourceOwnerPasswordCredentialsGrant(swaggerProperties.tokenUrl)
    return Arrays.asList<GrantType>(grantType)
  }

  private fun apiInfo(): ApiInfo {
    return ApiInfoBuilder()
      .title(swaggerProperties.apiInfo.title)
      .description(swaggerProperties.apiInfo.description)
      .license("无 License")
      .termsOfServiceUrl("http://www.canyannet.com/")
      .contact(Contact(
        "天津食健科技发展有限公司",
        "http://www.canyannet.com/",
        ""
      ))
      .version("3.0.0-SNAPSHOT")
      .build()
  }
}
