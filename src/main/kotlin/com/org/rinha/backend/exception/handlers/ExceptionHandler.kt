package com.org.rinha.backend.exception.handlers

import com.org.rinha.backend.exception.InvalidTransaction
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorInvalidField(
    val message: String,
    val invalidFields: List<Map<String, String>>,
)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<ErrorInvalidField> {
        val errors = ex.bindingResult.fieldErrors

        return ResponseEntity(
            ErrorInvalidField(
                message = "Your request parameters didn't validate.",
                invalidFields =
                    errors.map {
                        mapOf(
                            "field" to it.field,
                            "reason" to it.defaultMessage.orEmpty(),
                        )
                    },
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(InvalidTransaction::class)
    fun handleInvalidTransaction(ex: InvalidTransaction): ResponseEntity<*> {
        return ResponseEntity.unprocessableEntity().body("Invalid transaction")
    }
}
