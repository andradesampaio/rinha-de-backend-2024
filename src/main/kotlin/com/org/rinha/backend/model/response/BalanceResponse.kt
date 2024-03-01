package com.org.rinha.backend.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BalanceResponse(
    @JsonProperty("saldo")
    private val balance: Int = 0,
    @JsonProperty("limite")
    private val limit: Int = 0,
)