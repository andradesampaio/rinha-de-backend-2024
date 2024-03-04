package com.org.rinha.backend.repository

import com.org.rinha.backend.model.Cliente
import com.org.rinha.backend.model.Transaction

interface TransactionRepository {
    fun credit(transaction: Transaction): Pair<Int, Int>

    fun debit(transaction: Transaction): Pair<Int, Int>

    fun findClientById(clientId: Int): Cliente
}
