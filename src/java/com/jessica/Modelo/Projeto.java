/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class Projeto {

    private int identificador;
    private String titulo;
    private Date dataInicio;
    private Date dataTermino;
    private String agenciaFinanciadora;
    private float valorFinanciado;
    private String descricao;
    private String objetivo;
    private StatusProjeto status;
    private List<Colaborador> participantes;
    private List<Publicacao> publicacoes;

    public Projeto() {
        publicacoes = new ArrayList<>();
        participantes = new ArrayList<>();
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the dataInicio
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     * @param dataInicio the dataInicio to set
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * @return the dataTermino
     */
    public Date getDataTermino() {
        return dataTermino;
    }

    /**
     * @param dataTermino the dataTermino to set
     */
    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    /**
     * @return the agenciaFinanciadora
     */
    public String getAgenciaFinanciadora() {
        return agenciaFinanciadora;
    }

    /**
     * @param agenciaFinanciadora the agenciaFinanciadora to set
     */
    public void setAgenciaFinanciadora(String agenciaFinanciadora) {
        this.agenciaFinanciadora = agenciaFinanciadora;
    }

    /**
     * @return the valorFinanciado
     */
    public float getValorFinanciado() {
        return valorFinanciado;
    }

    /**
     * @param valorFinanciado the valorFinanciado to set
     */
    public void setValorFinanciado(float valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the objetivo
     */
    public String getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * @return the status
     */
    public StatusProjeto getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(StatusProjeto status) {
        this.status = status;
    }

    /**
     * @return the participantes
     */
    public List<Colaborador> getParticipantes() {
        return participantes;
    }

    /**
     * @param participantes the participantes to set
     */
    public void setParticipantes(List<Colaborador> participantes) {
        this.participantes = participantes;
    }

    /**
     * @return the publicacoes
     */
    public List<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    /**
     * @param publicacoes the publicacoes to set
     */
    public void setPublicacoes(List<Publicacao> publicacoes) {
        this.publicacoes = publicacoes;
    }

    public Projeto copiar(){
        Projeto novo = new Projeto();
        novo.setIdentificador(this.getIdentificador());
        novo.setTitulo(this.getTitulo());
        novo.setDataInicio(this.getDataInicio());
        novo.setDataTermino(this.getDataTermino());
        novo.setAgenciaFinanciadora(this.getAgenciaFinanciadora());
        novo.setValorFinanciado(this.getValorFinanciado());
        novo.setDescricao(this.getDescricao());
        novo.setObjetivo(this.getObjetivo());
        novo.setStatus(this.getStatus());
        
        novo.setPublicacoes(new ArrayList());
        novo.setParticipantes(new ArrayList());
        
        return novo;
    }

    public Projeto copiaSimples(){
        Projeto novo = new Projeto();
        novo.setIdentificador(this.getIdentificador());
        novo.setTitulo(this.getTitulo());
        novo.setDataInicio(this.getDataInicio());
        novo.setDataTermino(this.getDataTermino());
        novo.setAgenciaFinanciadora(this.getAgenciaFinanciadora());
        novo.setValorFinanciado(this.getValorFinanciado());
        novo.setDescricao(this.getDescricao());
        novo.setObjetivo(this.getObjetivo());
        novo.setStatus(this.getStatus());
        
        novo.setPublicacoes(new ArrayList());
        novo.setParticipantes(new ArrayList());
        for(Colaborador col : this.getParticipantes()){
            if(col instanceof Aluno)
                novo.getParticipantes().add(((Aluno)col).copiaSimples());
            if(col instanceof Professor)
                novo.getParticipantes().add(((Professor)col).copiaSimples());
            if(col instanceof Pesquisador)
                novo.getParticipantes().add(((Pesquisador)col).copiaSimples());
        }
        for(Publicacao pub : this.getPublicacoes())
            novo.getPublicacoes().add(pub.copiaSimples());
        
        return novo;
    }

}
