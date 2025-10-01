package com.sistemaacademico.model;

public class Aluno {
    private int idAluno;
    private String nome;
    private int idade;
    private String sexo;
    private double media;
    private double frequencia;
    private String rua;
    private int num;
    private String cep;
    private Integer monitor;
    private Integer monitorado;

    // Construtores
    public Aluno() {}

    public Aluno(String nome, int idade, String sexo, String rua, int num, String cep) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.rua = rua;
        this.num = num;
        this.cep = cep;
        this.frequencia = 100.0; // Valor padrão
    }

    // Getters e setters
    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
    public void setId(int id) { this.idAluno = id; } // Método para compatibilidade com controller

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public double getMedia() { return media; }
    public void setMedia(double media) { this.media = media; }

    public double getFrequencia() { return frequencia; }
    public void setFrequencia(double frequencia) { this.frequencia = frequencia; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public Integer getMonitor() { return monitor; }
    public void setMonitor(Integer monitor) { this.monitor = monitor; }

    public Integer getMonitorado() { return monitorado; }
    public void setMonitorado(Integer monitorado) { this.monitorado = monitorado; }

    @Override
    public String toString() {
        return "Aluno{" +
                "idAluno=" + idAluno +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", sexo='" + sexo + '\'' +
                ", media=" + media +
                ", frequencia=" + frequencia +
                '}';
    }
}