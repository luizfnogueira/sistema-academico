package com.sistemaacademico.model;

import java.sql.Date;

public class Avaliacao {
    private int idAvalia;
    private double valor;
    private Date data;
    private int idAluno;
    private int idDisc;

    // Getters e setters
    public int getIdAvalia() { return idAvalia; }
    public void setIdAvalia(int idAvalia) { this.idAvalia = idAvalia; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
    public int getIdDisc() { return idDisc; }
    public void setIdDisc(int idDisc) { this.idDisc = idDisc; }
}
