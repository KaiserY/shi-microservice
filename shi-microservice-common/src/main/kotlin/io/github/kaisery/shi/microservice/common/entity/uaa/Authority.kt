package io.github.kaisery.shi.microservice.common.entity.uaa

import io.github.kaisery.shi.microservice.common.entity.common.AbstractBaseEntity
import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "uaa_authority")
@Table(name = "uaa_authority")
class Authority : AbstractBaseEntity(), GrantedAuthority {

  @Column(name = "authority")
  private var authority: String = ""

  @Column(name = "description")
  var description = ""

  override fun getAuthority(): String {
    return authority
  }

  fun setAuthority(authority: String) {
    this.authority = authority
  }
}
