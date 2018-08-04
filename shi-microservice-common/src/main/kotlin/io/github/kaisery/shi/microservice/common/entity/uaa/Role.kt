package io.github.kaisery.shi.microservice.common.entity.uaa

import io.github.kaisery.shi.microservice.common.entity.common.AbstractBaseEntity
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity(name = "uaa_role")
@Table(name = "uaa_role")
class Role : AbstractBaseEntity(), GrantedAuthority {

  @Column(name = "authority")
  private var authority: String = ""

  @Column(name = "description")
  var description: String = ""

  @ManyToMany(
    cascade = [(CascadeType.PERSIST), (CascadeType.MERGE)]
  )
  @JoinTable(
    name = "uaa_role_authority_x",
    joinColumns = [(JoinColumn(name = "role_id", referencedColumnName = "id"))],
    inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "id")]
  )
  var authorities: Set<Authority>? = null

  override fun getAuthority(): String {
    return authority
  }

  fun setAuthority(authority: String) {
    this.authority = authority
  }
}
