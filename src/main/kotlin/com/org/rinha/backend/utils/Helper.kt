package com.org.rinha.backend.utils

import com.org.rinha.backend.model.Transaction
import com.org.rinha.backend.model.request.TransactionRequest
import java.time.LocalDateTime

fun convertTransactionDtoToTransaction(clientId: Int, transactionRequest: TransactionRequest) =
    Transaction(
        clientId = clientId,
        amount = transactionRequest.valor,
        type = transactionRequest.tipo,
        description = transactionRequest.descricao,
        createdAt = LocalDateTime.now()
    )