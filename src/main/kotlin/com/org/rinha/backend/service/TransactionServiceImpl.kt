package com.org.rinha.backend.service

import com.org.rinha.backend.model.Transaction
import com.org.rinha.backend.model.response.BalanceResponse
import com.org.rinha.backend.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(private val transactionRepository: TransactionRepository) : TransactionService {
    override fun createTransaction(transaction: Transaction): BalanceResponse {
        val (newBalance, availableLimit) = when (transaction.type) {
            Transaction.TransactionType.C -> transactionRepository.credit(transaction)
            Transaction.TransactionType.D -> transactionRepository.debit(transaction)
        }

        return BalanceResponse(
            balance = newBalance,
            limit = availableLimit,
        )
    }
}
