package com.org.rinha.backend.model

data class Transaction(
    val clientId: Int,
    val amount: Int,
    val type: TransactionType,
    val description: String,
) {
    enum class TransactionType {
        C,
        D,
    }
}
