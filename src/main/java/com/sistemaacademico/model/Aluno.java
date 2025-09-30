package com.sistemaacademico.model;

public class Aluno {

    private Long id;
    private String nome;
    private String curso;
    private Integer semestre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    // TODO: adicionar anotações (por exemplo, @Table, @Column) conforme o mapeamento for definido
}
