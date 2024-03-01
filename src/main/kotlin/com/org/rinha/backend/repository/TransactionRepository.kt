package com.org.rinha.backend.repository

import com.org.rinha.backend.model.Cliente
import com.org.rinha.backend.model.Transaction

interface TransactionRepository {
    fun save(transaction: Transaction)

    fun findClientById(clientId: Int): Cliente
}
