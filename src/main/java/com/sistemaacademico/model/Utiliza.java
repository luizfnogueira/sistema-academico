package com.sistemaacademico.model;

import java.sql.Date;
import java.sql.Time;

public class Utiliza {
    private int idTurma;
    private int idRecurso;
    private Date data;
    private Time hora;
    private String observacao;

    public int getIdTurma() { return idTurma; }
    public void setIdTurma(int idTurma) { this.idTurma = idTurma; }
    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}