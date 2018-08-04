package io.github.kaisery.shi.microservice.common.entity.uaa

import io.github.kaisery.shi.microservice.common.entity.common.AbstractAuditableEntity
import org.hibernate.annotations.Where
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity(name = "uaa_user")
@Table(name = "uaa_user")
@Where(clause = "is_deleted = 0")
class User() : AbstractAuditableEntity(), UserDetails {

  @Column(name = "username")
  private var username: String = ""

  @Column(name = "password")
  private var password: String = ""

  @Column(name = "account_non_expired")
  private var accountNonExpired: Boolean = true

  @Column(name = "account_non_locked")
  private var accountNonLocked: Boolean = true

  @Column(name = "credentials_non_expired")
  private var credentialsNonExpired: Boolean = true

  @Column(name = "enabled")
  private var enabled: Boolean = true

  @ManyToMany(
    cascade = [CascadeType.PERSIST, CascadeType.MERGE]
  )
  @JoinTable(
    name = "uaa_user_role_x",
    joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
    inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
  )
  var roles: Set<Role>? = null

  @Transient
  private var authorities: Collection<GrantedAuthority>? = null

  override fun getAuthorities(): Collection<GrantedAuthority>? {
    val authorities = HashSet<SimpleGrantedAuthority>()

    roles?.let { roleSet ->
      for (role in roleSet) {
        authorities.add(SimpleGrantedAuthority(role.getAuthority()))

        role.authorities?.let { authoritySet ->
          for (authority in authoritySet) {
            authorities.add(SimpleGrantedAuthority(authority.getAuthority()))
          }
        }
      }
    }

    return authorities
  }

  override fun isEnabled(): Boolean {
    return enabled
  }

  override fun getUsername(): String {
    return username
  }

  override fun isCredentialsNonExpired(): Boolean {
    return credentialsNonExpired
  }

  override fun getPassword(): String {
    return password
  }

  override fun isAccountNonExpired(): Boolean {
    return accountNonExpired
  }

  override fun isAccountNonLocked(): Boolean {
    return accountNonLocked
  }

  fun setUsername(username: String) {
    this.username = username
  }

  fun setPassword(password: String) {
    this.password = password
  }

  fun setAccountNonExpired(accountNonExpired: Boolean) {
    this.accountNonExpired = accountNonExpired
  }

  fun setCredentialsNonExpired(credentialsNonExpired: Boolean) {
    this.credentialsNonExpired = credentialsNonExpired
  }

  fun setEnabled(enabled: Boolean) {
    this.enabled = enabled
  }

  fun setAuthorities(authorities: Collection<GrantedAuthority>) {
    this.authorities = authorities
  }
}
