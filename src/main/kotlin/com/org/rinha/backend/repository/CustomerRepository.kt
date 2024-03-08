package com.org.rinha.backend.repository

import com.org.rinha.backend.model.CustomerBalanceAndLimit

interface CustomerRepository {
    fun getBalanceById(id: Int): CustomerBalanceAndLimit
}