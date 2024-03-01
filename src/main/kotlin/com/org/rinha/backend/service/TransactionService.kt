package com.org.rinha.backend.service

import com.org.rinha.backend.model.Transaction
import com.org.rinha.backend.model.response.BalanceResponse

interface TransactionService{
    fun createTransaction(transaction: Transaction): BalanceResponse
}