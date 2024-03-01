package com.org.rinha.backend.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class TransactionRequest(
    @field:Min(value = 1, message = "Amount must not be blank")
    val valor: Int,
    @field:[NotBlank Pattern(regexp="c|d",  message = "Only c|d pattern")]
    val tipo: String,
    @field:NotBlank(message = "Description must not be blank")
    val descricao: String
)