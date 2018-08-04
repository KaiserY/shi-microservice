package io.github.kaisery.shi.microservice.uaa.repository

import io.github.kaisery.shi.microservice.common.entity.uaa.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, String>, JpaSpecificationExecutor<User> {

  @Query("select u from uaa_user u join fetch u.roles r join fetch r.authorities where u.username = :username")
  fun findByUsername(@Param("username") username: String): User?
}
