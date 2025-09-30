package com.sistemaacademico.model;

public class Pesquisa {
    private int idPesquisa;
    private int freqRecurso;
    private String nvlEstresse;
    private int qtdDisciplinas;
    private int freqEstudo;
    private int idAluno;

    // Getters e setters
    public int getIdPesquisa() { return idPesquisa; }
    public void setIdPesquisa(int idPesquisa) { this.idPesquisa = idPesquisa; }
    public int getFreqRecurso() { return freqRecurso; }
    public void setFreqRecurso(int freqRecurso) { this.freqRecurso = freqRecurso; }
    public String getNvlEstresse() { return nvlEstresse; }
    public void setNvlEstresse(String nvlEstresse) { this.nvlEstresse = nvlEstresse; }
    public int getQtdDisciplinas() { return qtdDisciplinas; }
    public void setQtdDisciplinas(int qtdDisciplinas) { this.qtdDisciplinas = qtdDisciplinas; }
    public int getFreqEstudo() { return freqEstudo; }
    public void setFreqEstudo(int freqEstudo) { this.freqEstudo = freqEstudo; }
    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
}
