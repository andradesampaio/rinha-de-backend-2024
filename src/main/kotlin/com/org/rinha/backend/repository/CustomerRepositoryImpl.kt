package com.org.rinha.backend.repository

import com.org.rinha.backend.model.CustomerBalanceAndLimit
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class CustomerRepositoryImpl(private val dataSource: DataSource) : CustomerRepository {
    private val jdbcTemplate = JdbcTemplate(dataSource)

    override fun getBalanceById(id: Int): CustomerBalanceAndLimit {
        val query =
            "select  c.\"limit\" - c.limit_in_use as availableLimit, c.balance from customers c" +
            " where c.id = ?;"

        val rowMapper = BeanPropertyRowMapper(CustomerBalanceAndLimit::class.java)

        return jdbcTemplate.queryForStream(query, rowMapper, id)
            .findFirst()
            .orElseThrow { RuntimeException("Customer not found") }
    }

}