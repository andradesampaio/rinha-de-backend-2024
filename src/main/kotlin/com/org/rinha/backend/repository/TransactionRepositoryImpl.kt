package com.org.rinha.backend.repository

import com.org.rinha.backend.exception.InvalidTransaction
import com.org.rinha.backend.model.Cliente
import com.org.rinha.backend.model.Transaction
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

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


    override fun findClientById(clientId: Int): Cliente {
        val query =
            "SELECT cliente.id as clienteId, cliente.nome, cliente.limite, saldo.id as saldoId, saldo.valor as saldoValor " +
                "FROM clientes as cliente, saldos as saldo " +
                "WHERE cliente.id = saldo.id " +
                "AND cliente.id = ?"

        val rowMapper = BeanPropertyRowMapper(Cliente::class.java)

        return jdbcTemplate.queryForStream(query, rowMapper, clientId)
            .findFirst()
            .orElseThrow { RuntimeException("Client not found") }
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
