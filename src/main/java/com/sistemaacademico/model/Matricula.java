package com.sistemaacademico.model;

import java.sql.Date;

public class Matricula {
    private int idMatricula;
    private Date data;
    private int idAluno;
    private int idTurma;
    
    public int getIdMatricula() { return idMatricula; }
    public void setIdMatricula(int idMatricula) { this.idMatricula = idMatricula; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
    public int getIdTurma() { return idTurma; } // Adicionado
    public void setIdTurma(int idTurma) { this.idTurma = idTurma; } // Adicionado
}