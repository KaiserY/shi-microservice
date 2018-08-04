package io.github.kaisery.shi.microservice.common.entity.common

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class AbstractBaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0
}

@MappedSuperclass
abstract class AbstractSoftDeleteEntity : AbstractBaseEntity() {
  @Column(name = "is_deleted")
  var deleted: Boolean = false
}

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractAuditableEntity : AbstractSoftDeleteEntity() {
  @Column(name = "created_date", updatable = false)
  @CreatedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
  var createdDate: LocalDateTime? = null

  @Column(name = "last_modified_date")
  @LastModifiedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
  var lastModifiedDate: LocalDateTime? = null

  @Column(name = "created_by")
  @CreatedBy
  var createdBy: String? = null

  @Column(name = "last_modified_by")
  @LastModifiedBy
  var lastModifiedBy: String? = null
}
