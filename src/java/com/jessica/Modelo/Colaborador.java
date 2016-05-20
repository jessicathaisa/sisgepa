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
public class Colaborador {
    private int identificador;
    private Usuario usuario;
    private String nome;
    private String email;
    private List<ProducaoAcademica> producoes;
    private List<Projeto> projetos;
    protected TipoColaborador tipoColaborador;

    public Colaborador() {
        this.projetos = new ArrayList<>();
        this.producoes = new ArrayList<>();
    }
    
    /**
     * @return the projetos
     */
    public List<Projeto> getProjetos() {
        return projetos;
    }

    /**
     * @param projetos the projetos to set
     */
    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    /**
     * @return the identificador
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the producoes
     */
    public List<ProducaoAcademica> getProducoes() {
        return producoes;
    }

    /**
     * @param producoes the producoes to set
     */
    public void setProducoes(List<ProducaoAcademica> producoes) {
        this.producoes = producoes;
    }

    /**
     * @return the tipoColaborador
     */
    public TipoColaborador getTipoColaborador() {
        return tipoColaborador;
    }

    /**
     * @param tipoColaborador the tipoColaborador to set
     */
    public void setTipoColaborador(TipoColaborador tipoColaborador) {
        this.tipoColaborador = tipoColaborador;
    }
}
