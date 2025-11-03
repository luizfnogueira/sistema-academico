package com.sistemaacademico.model;

public class Disciplina {
    private int idDisc;
    private String nome;
    private int cargaHoraria;
    

    public Disciplina() {}

    public Disciplina(String nome, int cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public int getIdDisc() { return idDisc; }
    public void setIdDisc(int idDisc) { this.idDisc = idDisc; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    @Override
    public String toString() {
        return "Disciplina{" +
                "idDisc=" + idDisc +
                ", nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                '}';
    }
}