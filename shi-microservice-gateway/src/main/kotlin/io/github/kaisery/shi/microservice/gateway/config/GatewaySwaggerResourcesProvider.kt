package io.github.kaisery.shi.microservice.gateway.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import springfox.documentation.swagger.web.SwaggerResource
import springfox.documentation.swagger.web.SwaggerResourcesProvider
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@Primary
@EnableSwagger2
class GatewaySwaggerResourcesProvider : SwaggerResourcesProvider {

  override fun get(): MutableList<SwaggerResource> {
    return arrayListOf(
      swaggerResource("uaa", "/uaa/v2/api-docs"),
      swaggerResource("demo", "/demo/v2/api-docs")
    )
  }

  fun swaggerResource(name: String, location: String): SwaggerResource {
    val swaggerResource = SwaggerResource()
    swaggerResource.name = name
    swaggerResource.location = location
    swaggerResource.swaggerVersion = "2.0"

    return swaggerResource
  }
}
