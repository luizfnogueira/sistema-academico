package com.sistemaacademico.model;

public class Disciplina {
    private int idDisc;
    private String nome;
    private int cargaHoraria;
    private int idAluno;
    private int idTurma;

    // Getters e setters
    public int getIdDisc() { return idDisc; }
    public void setIdDisc(int idDisc) { this.idDisc = idDisc; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
    public int getIdTurma() { return idTurma; }
    public void setIdTurma(int idTurma) { this.idTurma = idTurma; }
}
