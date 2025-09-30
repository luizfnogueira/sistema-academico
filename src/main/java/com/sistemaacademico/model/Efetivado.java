package com.sistemaacademico.model;

import java.sql.Date;

public class Efetivado {
    private int idProf;
    private double salario;
    private Date dataConcurso;
    private String regime;

    // Getters e setters
    public int getIdProf() { return idProf; }
    public void setIdProf(int idProf) { this.idProf = idProf; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public Date getDataConcurso() { return dataConcurso; }
    public void setDataConcurso(Date dataConcurso) { this.dataConcurso = dataConcurso; }
    public String getRegime() { return regime; }
    public void setRegime(String regime) { this.regime = regime; }
}
