package com.org.rinha.backend.model

import java.time.LocalDateTime

data class Transaction(
     val clientId: Int,
     val amount: Int,
     val type: String,
     val description: String,
     val createdAt: LocalDateTime
)