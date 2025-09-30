.package com.sistemaacademico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.Repositorio.UserRepository;

@SpringBootApplication
public class SistemaAcademicoApplication {

    private static UserRepository repositorioUsuario;
    
    public SistemaAcademicoApplication(UserRepository repositorioUsuario) {
        SistemaAcademicoApplication.repositorioUsuario = repositorioUsuario;
    }
    public static void main(String[] args) {
        SpringApplication.run(SistemaAcademicoApplication.class, args);
        repositorioUsuario.criarTabela();
    }
}
