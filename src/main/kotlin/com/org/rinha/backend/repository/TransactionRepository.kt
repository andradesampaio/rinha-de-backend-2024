package com.org.rinha.backend.repository

import com.org.rinha.backend.model.CustomerBalanceAndLimit
import com.org.rinha.backend.model.Transaction

interface TransactionRepository {
    fun credit(transaction: Transaction): Pair<Int, Int>

    fun debit(transaction: Transaction): Pair<Int, Int>

    fun getLastTransactions(customerId: Int, maxItems: Int): List<Transactions>
}
