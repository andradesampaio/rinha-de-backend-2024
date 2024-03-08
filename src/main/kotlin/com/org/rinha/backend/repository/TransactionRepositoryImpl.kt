package com.org.rinha.backend.repository

import com.fasterxml.jackson.annotation.JsonProperty
import com.org.rinha.backend.exception.InvalidTransaction
import com.org.rinha.backend.model.CustomerBalanceAndLimit
import com.org.rinha.backend.model.Transaction
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.sql.DataSource

data class Transactions(
    var amount: Int = 0,
    var type: String = "",
    var description: String = "",
    @JsonProperty("transacted_in")
    var transactedIn: LocalDateTime = LocalDateTime.now()
)

@Repository
class TransactionRepositoryImpl(private val dataSource: DataSource) : TransactionRepository {
    private val jdbcTemplate = JdbcTemplate(dataSource)

    override fun debit(transaction: Transaction): Pair<Int, Int> =
        executeTransaction(
            "SELECT new_balance, available_limit FROM debit(?, ?, ?)",
            transaction
        )

    override fun credit(transaction: Transaction): Pair<Int, Int> =
        executeTransaction(
            "SELECT new_balance, available_limit FROM credit(?, ?, ?)",
            transaction
        )

    override fun getLastTransactions(customerId: Int, maxItems: Int): List<Transactions> {
        val query =
            "select t.amount, t.\"type\", t.description, t.transacted_in " +
            "  from transactions t" +
            " where t.customer_id = ?" +
            " limit ?;"

        val rowMapper = BeanPropertyRowMapper(Transactions::class.java)

        return jdbcTemplate.queryForStream(query, rowMapper, customerId, maxItems).toList() ?: throw InvalidTransaction()
    }

    private fun executeTransaction(
        sql: String,
        transaction: Transaction
    ): Pair<Int, Int> {
        try {
            return jdbcTemplate.query(
                sql,
                arrayOf(
                    transaction.clientId,
                    transaction.amount,
                    transaction.description
                )
            ) { rs, _ ->
                Pair(
                    rs.getInt("new_balance"),
                    rs.getInt("available_limit")
                )
            }
                .first()
        } catch (_: Exception) {
            throw InvalidTransaction()
        }
    }
}
