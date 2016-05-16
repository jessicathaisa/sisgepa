/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class Publicacao extends ProducaoAcademica {

    private List<Colaborador> autores;
    private String conferencia;
    private Projeto projeto;

    public Publicacao() {
        autores = new ArrayList<>();
    }

    /**
     * @return the autores
     */
    public List<Colaborador> getAutores() {
        return autores;
    }

    /**
     * @param autores the autores to set
     */
    public void setAutores(List<Colaborador> autores) {
        this.autores = autores;
    }

    /**
     * @return the projeto
     */
    public Projeto getProjeto() {
        return projeto;
    }

    /**
     * @param projeto the projeto to set
     */
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    /**
     * @return the conferencia
     */
    public String getConferencia() {
        return conferencia;
    }

    /**
     * @param conferencia the conferencia to set
     */
    public void setConferencia(String conferencia) {
        this.conferencia = conferencia;
    }

    public Publicacao copiar() {
        Publicacao novo = new Publicacao();

        novo.setIdentificador(this.getIdentificador());
        novo.setTitulo(this.getTitulo());
        novo.setAno(this.getAno());
        novo.setConferencia(this.getConferencia());
        
        if(this.getProjeto() != null)
            novo.setProjeto(this.getProjeto().copiar());

        for (Colaborador p : this.getAutores()) {
            if (p instanceof Aluno) {
                novo.getAutores().add(((Aluno) p).copiar());
            }
            if (p instanceof Professor) {
                novo.getAutores().add(((Professor) p).copiar());
            }
            if (p instanceof Pesquisador) {
                novo.getAutores().add(((Pesquisador) p).copiar());
            }
        }

        return novo;
    }

    public Publicacao copiaSimples() {
        Publicacao novo = new Publicacao();

        novo.setIdentificador(this.getIdentificador());
        novo.setTitulo(this.getTitulo());
        novo.setAno(this.getAno());
        novo.setConferencia(this.getConferencia());

        if(this.getProjeto() != null)
            novo.setProjeto(this.getProjeto().copiar());
        
        for (Colaborador p : this.getAutores()) {
            if (p instanceof Aluno) {
                novo.getAutores().add(((Aluno) p).copiaSimples());
            }
            if (p instanceof Professor) {
                novo.getAutores().add(((Professor) p).copiaSimples());
            }
            if (p instanceof Pesquisador) {
                novo.getAutores().add(((Pesquisador) p).copiaSimples());
            }
        }

        return novo;
    }

}
