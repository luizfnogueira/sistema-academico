package com.sistemaacademico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Configuração do banco de dados
 * 
 * NOTA: As configurações de conexão (URL, usuário, senha) estão no 
 * application.properties. O Spring Boot configura automaticamente o DataSource
 * a partir dessas propriedades.
 * 
 * Este arquivo mantém apenas o JdbcTemplate como Bean.
 */
@Configuration
public class DatabaseConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
