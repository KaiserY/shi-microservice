package io.github.kaisery.shi.microservice.common.entity.common

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "biz_log")
@Table(name = "biz_log")
class BizLog(
  val principal: String,
  val type: String,
  val timestamp: LocalDateTime
) : AbstractBaseEntity()
