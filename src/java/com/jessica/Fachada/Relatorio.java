/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

/**
 *
 * @author Jessica
 */
public class Relatorio {
    private int totalColaboradores;
    private int totalProjetos;
    private int totalProjetosEmElaboracao;
    private int totalProjetosEmAndamento;
    private int totalProjetosConcluidos;
    private int totalOrientacoes;
    private int totalPublicacoes;

    /**
     * @return the totalColaboradores
     */
    public int getTotalColaboradores() {
        return totalColaboradores;
    }

    /**
     * @param totalColaboradores the totalColaboradores to set
     */
    public void setTotalColaboradores(int totalColaboradores) {
        this.totalColaboradores = totalColaboradores;
    }

    /**
     * @return the totalProjetos
     */
    public int getTotalProjetos() {
        return totalProjetos;
    }

    /**
     * @param totalProjetos the totalProjetos to set
     */
    public void setTotalProjetos(int totalProjetos) {
        this.totalProjetos = totalProjetos;
    }

    /**
     * @return the totalProjetosEmElaboracao
     */
    public int getTotalProjetosEmElaboracao() {
        return totalProjetosEmElaboracao;
    }

    /**
     * @param totalProjetosEmElaboracao the totalProjetosEmElaboracao to set
     */
    public void setTotalProjetosEmElaboracao(int totalProjetosEmElaboracao) {
        this.totalProjetosEmElaboracao = totalProjetosEmElaboracao;
    }

    /**
     * @return the totalProjetosEmAndamento
     */
    public int getTotalProjetosEmAndamento() {
        return totalProjetosEmAndamento;
    }

    /**
     * @param totalProjetosEmAndamento the totalProjetosEmAndamento to set
     */
    public void setTotalProjetosEmAndamento(int totalProjetosEmAndamento) {
        this.totalProjetosEmAndamento = totalProjetosEmAndamento;
    }

    /**
     * @return the totalProjetosConcluidos
     */
    public int getTotalProjetosConcluidos() {
        return totalProjetosConcluidos;
    }

    /**
     * @param totalProjetosConcluidos the totalProjetosConcluidos to set
     */
    public void setTotalProjetosConcluidos(int totalProjetosConcluidos) {
        this.totalProjetosConcluidos = totalProjetosConcluidos;
    }

    /**
     * @return the totalOrientacoes
     */
    public int getTotalOrientacoes() {
        return totalOrientacoes;
    }

    /**
     * @param totalOrientacoes the totalOrientacoes to set
     */
    public void setTotalOrientacoes(int totalOrientacoes) {
        this.totalOrientacoes = totalOrientacoes;
    }

    /**
     * @return the totalPublicacoes
     */
    public int getTotalPublicacoes() {
        return totalPublicacoes;
    }

    /**
     * @param totalPublicacoes the totalPublicacoes to set
     */
    public void setTotalPublicacoes(int totalPublicacoes) {
        this.totalPublicacoes = totalPublicacoes;
    }
}
