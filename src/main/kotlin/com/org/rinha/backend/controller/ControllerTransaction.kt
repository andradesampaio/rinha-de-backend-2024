package com.org.rinha.backend.controller

import com.org.rinha.backend.model.request.TransactionRequest
import com.org.rinha.backend.model.response.TransactionResponse
import com.org.rinha.backend.model.response.TransactionsResponse
import com.org.rinha.backend.service.TransactionService
import com.org.rinha.backend.utils.convertTransactionDtoToTransaction
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ControllerTransaction(
    private val transactionService: TransactionService
) {
    @PostMapping("/{clientId}/transacoes")
    fun createTransaction(
        @PathVariable clientId: Int,
        @Valid @RequestBody transaction: TransactionRequest,
    ): ResponseEntity<TransactionResponse> =
        transactionService.createTransaction(
            convertTransactionDtoToTransaction(clientId, transaction)
        ).let {
            ResponseEntity(
                it,
                HttpStatus.OK
            )
        }


    @GetMapping("/{customerId}/extrato")
    fun getBalance(
        @PathVariable customerId: Int,
    ): ResponseEntity<TransactionsResponse> = ResponseEntity(
        transactionService.getTransactions(customerId),
        HttpStatus.OK
    )
}
