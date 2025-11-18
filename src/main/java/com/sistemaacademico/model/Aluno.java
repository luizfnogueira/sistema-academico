package com.sistemaacademico.model;

import java.sql.Date; // Importar java.sql.Date

public class Aluno {
    private int idAluno;
    private String nome;
    private int idade;
    private String sexo;
    private Date dataNasc; 
    private double media;
    private double frequencia;
    private String rua;
    private int num;
    private String cep;
    private String statusPagamento;
    
    public Aluno() {}

    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
    public void setId(int id) { this.idAluno = id; } // Método para compatibilidade

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Date getDataNasc() { return dataNasc; } // Adicionado
    public void setDataNasc(Date dataNasc) { this.dataNasc = dataNasc; } // Adicionado

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

    public String getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }

    @Override
    public String toString() {
        return "Aluno{" +
                "idAluno=" + idAluno +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", sexo='" + sexo + '\'' +
                ", media=" + media +
                '}';
    }
}