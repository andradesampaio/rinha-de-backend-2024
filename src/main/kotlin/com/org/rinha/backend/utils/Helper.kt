package com.org.rinha.backend.utils

import com.org.rinha.backend.model.Transaction
import com.org.rinha.backend.model.request.TransactionRequest

fun convertTransactionDtoToTransaction(
    clientId: Int,
    transactionRequest: TransactionRequest,
) = Transaction(
    clientId = clientId,
    amount = transactionRequest.valor,
    type = Transaction.TransactionType.valueOf(transactionRequest.tipo.uppercase()),
    description = transactionRequest.descricao,
)
