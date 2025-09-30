package com.sistemaacademico.model;

public class Professor {
    private int idProf;
    private String cpf;
    private String nome;
    private String rua;
    private int num;
    private String cep;

    // Getters e setters
    public int getIdProf() { return idProf; }
    public void setIdProf(int idProf) { this.idProf = idProf; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}
