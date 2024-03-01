package com.org.rinha.backend.repository

import com.org.rinha.backend.model.Cliente
import com.org.rinha.backend.model.Transaction
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class TransactionRepositoryImpl(private val dataSource: DataSource) : TransactionRepository {
    private val jdbcTemplate = JdbcTemplate(dataSource)

    override fun save(transaction: Transaction) {
        val sql = "INSERT INTO transacoes (cliente_id, valor, tipo, descricao, realizada_em) VALUES (?, ?, ?, ?, ?)"
        jdbcTemplate.update(
            sql,
            transaction.clientId,
            transaction.amount,
            transaction.type,
            transaction.description,
            transaction.createdAt,
        )
    }

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
}
