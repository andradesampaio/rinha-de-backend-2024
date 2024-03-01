package com.org.rinha.backend.service

import com.org.rinha.backend.model.Transaction
import com.org.rinha.backend.model.response.BalanceResponse
import com.org.rinha.backend.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(private val transactionRepository: TransactionRepository) : TransactionService {

    override fun createTransaction(transaction: Transaction): BalanceResponse {
        val client = transactionRepository.findClientById(transaction.clientId)
        transactionRepository.save(transaction)
        return BalanceResponse(
            balance = client.saldoValor,
            limit = client.limite,
        )
    }
}
