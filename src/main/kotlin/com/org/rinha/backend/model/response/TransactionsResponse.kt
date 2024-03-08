package com.org.rinha.backend.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class TransactionsResponse (
    val balance: Balance,
    @JsonProperty("ultimas_transacoes")
    val ultimasTransacoes: List<Transacao>
) {
    data class Balance(
        val total: Int,
        @JsonProperty("data_extrato")
        val dataExtrato: LocalDateTime = LocalDateTime.now(),
        val limite: Int
    )

    data class Transacao(
        val valor: Int,
        val tipo: String,
        val descricao: String,
        @JsonProperty("realizada_em")
        val realizadaEm: LocalDateTime
    )
}