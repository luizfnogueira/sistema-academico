package com.sistemaacademico.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ConsultaRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TODO: escrever consultas SQL explícitas conforme exigido pelo projeto
}
