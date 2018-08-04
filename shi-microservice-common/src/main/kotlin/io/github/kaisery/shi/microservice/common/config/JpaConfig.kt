package io.github.kaisery.shi.microservice.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaConfig {

  @Bean
  fun auditorAware(): AuditorAware<String> {
    return SpringSecurityAuditorAware()
  }
}

class SpringSecurityAuditorAware : AuditorAware<String> {

  override fun getCurrentAuditor(): Optional<String> {
    val authentication = SecurityContextHolder.getContext().authentication

    return if (authentication == null || !authentication.isAuthenticated) {
      Optional.empty()
    } else Optional.of(authentication.name)

  }

}
