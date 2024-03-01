package com.org.rinha.backend.model

data class Cliente(
    var clienteId: Int = 0,
    var nome: String = "",
    var limite: Int = 0,
    var saldoId: Long = Long.MIN_VALUE,
    var saldoValor: Int = 0,
)
