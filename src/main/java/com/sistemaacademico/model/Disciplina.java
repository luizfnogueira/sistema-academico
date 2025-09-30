package com.sistemaacademico.model;

public class Disciplina {
    private int idDisc;
    private String nome;
    private int cargaHoraria;
    private Integer idAluno;
    private Integer idTurma;

    // Construtores
    public Disciplina() {}

    public Disciplina(String nome, int cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    // Getters e setters
    public int getIdDisc() { return idDisc; }
    public void setIdDisc(int idDisc) { this.idDisc = idDisc; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    
    public Integer getIdAluno() { return idAluno; }
    public void setIdAluno(Integer idAluno) { this.idAluno = idAluno; }
    
    public Integer getIdTurma() { return idTurma; }
    public void setIdTurma(Integer idTurma) { this.idTurma = idTurma; }

    @Override
    public String toString() {
        return "Disciplina{" +
                "idDisc=" + idDisc +
                ", nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                '}';
    }
}
