package com.org.rinha.backend.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class TransactionRequest(
    @field:Min(value = 1)
    val valor: Int,
    @field:[
    NotBlank
    Pattern(regexp = "c|d", message = "Invalid value, accepted values C or D")
    ]
    val tipo: String,
    @field:NotBlank
    val descricao: String,
)
