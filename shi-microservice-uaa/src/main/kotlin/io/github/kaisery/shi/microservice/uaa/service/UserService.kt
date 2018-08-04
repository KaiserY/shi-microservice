package io.github.kaisery.shi.microservice.uaa.service

import io.github.kaisery.shi.microservice.common.dto.UserResDto
import io.github.kaisery.shi.microservice.common.entity.uaa.Authority
import io.github.kaisery.shi.microservice.common.entity.uaa.Role
import io.github.kaisery.shi.microservice.common.entity.uaa.User
import io.github.kaisery.shi.microservice.uaa.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
  val passwordEncoder: PasswordEncoder,
  val userRepository: UserRepository,
  val modelMapper: ModelMapper
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    return userRepository.findByUsername(username)
      ?: throw UsernameNotFoundException("No user found with username $username")
  }

  fun findByUsername(user: User): User? {
    return userRepository.findByUsername(user.username)
  }

  fun signup(user: User): UserResDto {
    val signedUser = findByUsername(user)

    if (signedUser == null) {
      user.password = passwordEncoder.encode(user.password)

      val userRole = Role()
      userRole.authority = "ROLE_USER"
      userRole.description = "ROLE_USER"

      val readAuthority = Authority()
      readAuthority.authority = "AUTH_READ"
      readAuthority.description = "AUTH_READ"

      userRole.authorities = setOf(readAuthority)
      user.roles = setOf(userRole)

      return modelMapper.map(userRepository.save(user), UserResDto::class.java)
    } else {
      val userResDto = modelMapper.map(signedUser, UserResDto::class.java)
      userResDto.state = 1
      return userResDto
    }
  }
}
