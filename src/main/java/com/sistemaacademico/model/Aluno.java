package com.sistemaacademico.model;

public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private String sexo;
    private double media;
    private double frequencia;
    private String rua;
    private int num;
    private String cep;
    private String monitor;
    private String monitorado;

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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

    public String getMonitor() { return monitor; }
    public void setMonitor(String monitor) { this.monitor = monitor; }

    public String getMonitorado() { return monitorado; }
    public void setMonitorado(String monitorado) { this.monitorado = monitorado; }
}