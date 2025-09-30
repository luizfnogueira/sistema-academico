package com.sistemaacademico.model;

import java.sql.Date;

public class Conselho {
    private int idConselho;
    private String descricao;
    private Date data;
    private int idProf;

    // Getters e setters
    public int getIdConselho() { return idConselho; }
    public void setIdConselho(int idConselho) { this.idConselho = idConselho; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public int getIdProf() { return idProf; }
    public void setIdProf(int idProf) { this.idProf = idProf; }
}
