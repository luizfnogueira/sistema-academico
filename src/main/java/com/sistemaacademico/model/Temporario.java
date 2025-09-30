package com.sistemaacademico.model;

import java.sql.Date;

public class Temporario {
    private int idProf;
    private double remuneracao;
    private Date inicio;
    private Date fim;

    // Getters e setters
    public int getIdProf() { return idProf; }
    public void setIdProf(int idProf) { this.idProf = idProf; }
    public double getRemuneracao() { return remuneracao; }
    public void setRemuneracao(double remuneracao) { this.remuneracao = remuneracao; }
    public Date getInicio() { return inicio; }
    public void setInicio(Date inicio) { this.inicio = inicio; }
    public Date getFim() { return fim; }
    public void setFim(Date fim) { this.fim = fim; }
}
