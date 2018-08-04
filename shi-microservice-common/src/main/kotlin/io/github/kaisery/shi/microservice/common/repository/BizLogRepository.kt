package io.github.kaisery.shi.microservice.common.repository

import io.github.kaisery.shi.microservice.common.entity.common.BizLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BizLogRepository : JpaRepository<BizLog, String>, JpaSpecificationExecutor<BizLog>
