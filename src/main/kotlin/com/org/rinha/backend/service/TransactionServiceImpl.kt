package com.org.rinha.backend.service

import com.org.rinha.backend.model.Transaction
import com.org.rinha.backend.model.response.TransactionResponse
import com.org.rinha.backend.model.response.TransactionsResponse
import com.org.rinha.backend.repository.CustomerRepository
import com.org.rinha.backend.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val customerRepository: CustomerRepository
) : TransactionService {
    override fun createTransaction(transaction: Transaction): TransactionResponse {
        val (newBalance, availableLimit) = when (transaction.type) {
            Transaction.TransactionType.C -> transactionRepository.credit(transaction)
            Transaction.TransactionType.D -> transactionRepository.debit(transaction)
        }

        return TransactionResponse(
            balance = newBalance,
            limit = availableLimit,
        )
    }

    override fun getTransactions(customerId: Int): TransactionsResponse {

        val balanceAndLimit = customerRepository.getBalanceById(customerId)
        val transactions = transactionRepository.getLastTransactions(customerId, 10)

        return TransactionsResponse(
            balance = TransactionsResponse.Balance(
                total = balanceAndLimit.balance,
                limite = balanceAndLimit.availableLimit
            ),
            ultimasTransacoes = transactions.map {
                TransactionsResponse.Transacao(
                    valor = it.amount,
                    tipo = it.type,
                    descricao = it.description,
                    realizadaEm = it.transactedIn
                )
            }
        )
    }
}
