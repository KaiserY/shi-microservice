package io.github.kaisery.shi.microservice.common.config

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class GeneralConfig {

  @Bean
  @Primary
  fun modelMapper(): ModelMapper {
    val modelMapper = ModelMapper()
    modelMapper.configuration.isAmbiguityIgnored = false
    modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
    modelMapper.configuration.isDeepCopyEnabled = true

    return modelMapper
  }
}
