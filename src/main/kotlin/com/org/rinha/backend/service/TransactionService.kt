package com.org.rinha.backend.service

import com.org.rinha.backend.model.Transaction
import com.org.rinha.backend.model.response.TransactionResponse
import com.org.rinha.backend.model.response.TransactionsResponse

interface TransactionService{
    fun createTransaction(transaction: Transaction): TransactionResponse

    fun getTransactions(customerId: Int): TransactionsResponse
}