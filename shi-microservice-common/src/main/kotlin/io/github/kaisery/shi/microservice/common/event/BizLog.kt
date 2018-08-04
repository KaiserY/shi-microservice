package io.github.kaisery.shi.microservice.common.event

import io.github.kaisery.shi.microservice.common.entity.common.BizLog
import io.github.kaisery.shi.microservice.common.repository.BizLogRepository
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

class BizLogEvent(
  private val principal: String,
  private val type: String,
  private val data: Map<String, Any>
) : AuditApplicationEvent(principal, type, data)

@Component
class BizLogAuditListener(
  val bizLogRepository: BizLogRepository
) {

  @EventListener
  fun handleBizAuditApplicationEvent(bizLogEvent: BizLogEvent) {
    bizLogRepository.save(
      BizLog(
        bizLogEvent.auditEvent.principal,
        bizLogEvent.auditEvent.type,
        LocalDateTime.ofInstant(bizLogEvent.auditEvent.timestamp, ZoneOffset.UTC)
      )
    )
  }
}
