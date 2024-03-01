package com.org.rinha.backend.controller

import com.org.rinha.backend.model.request.TransactionRequest
import com.org.rinha.backend.model.response.BalanceResponse
import com.org.rinha.backend.service.TransactionService
import com.org.rinha.backend.utils.convertTransactionDtoToTransaction
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ControllerTransaction(private val transactionService: TransactionService) {
    @PostMapping("/{clientId}/transacoes")
    @ResponseBody
    fun createTransaction(
        @PathVariable clientId: Int,
        @Valid @RequestBody transaction: TransactionRequest,
    ): ResponseEntity<BalanceResponse> {
        val transactionResponse = transactionService.createTransaction(convertTransactionDtoToTransaction(clientId, transaction))
        return ResponseEntity.ok().body(transactionResponse)
    }

    @GetMapping("/{clientId}/extrato")
    fun getBalance(
        @PathVariable clientId: Int,
    ): ResponseEntity<String> {
        return ResponseEntity.ok("extrato")
    }
}
