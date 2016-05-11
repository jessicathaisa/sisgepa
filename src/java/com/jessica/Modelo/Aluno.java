/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Modelo;

import java.util.Date;

/**
 *
 * @author Jessica
 */
public class Aluno extends Colaborador {
    private Date dataIngresso;
    private RegimeCurso regimeCurso;
    private TipoProjetoAluno tipoProjeto;
    private TipoAluno tipoAluno;
    private Professor orientador;

    /**
     * @return the regimeCurso
     */
    public RegimeCurso getRegimeCurso() {
        return regimeCurso;
    }

    /**
     * @param regimeCurso the regimeCurso to set
     */
    public void setRegimeCurso(RegimeCurso regimeCurso) {
        this.regimeCurso = regimeCurso;
    }

    /**
     * @return the tipoProjeto
     */
    public TipoProjetoAluno getTipoProjeto() {
        return tipoProjeto;
    }

    /**
     * @param tipoProjeto the tipoProjeto to set
     */
    public void setTipoProjeto(TipoProjetoAluno tipoProjeto) {
        this.tipoProjeto = tipoProjeto;
    }

    /**
     * @return the tipoAluno
     */
    public TipoAluno getTipoAluno() {
        return tipoAluno;
    }

    /**
     * @param tipoAluno the tipoAluno to set
     */
    public void setTipoAluno(TipoAluno tipoAluno) {
        this.tipoAluno = tipoAluno;
    }

    /**
     * @return the orientador
     */
    public Professor getOrientador() {
        return orientador;
    }

    /**
     * @param orientador the orientador to set
     */
    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    /**
     * @return the dataIngresso
     */
    public Date getDataIngresso() {
        return dataIngresso;
    }

    /**
     * @param dataIngresso the dataIngresso to set
     */
    public void setDataIngresso(Date dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    public Aluno copiar(){
       Aluno novo = new Aluno();
       
       novo.setIdentificador(this.getIdentificador());
       novo.setNome(this.getNome());
       novo.setEmail(this.getEmail());
       novo.setDataIngresso(this.getDataIngresso());
       novo.setTipoAluno(this.getTipoAluno());
       novo.setRegimeCurso(this.getRegimeCurso());
       
       novo.setTipoProjeto(this.getTipoProjeto());
       novo.setOrientador(this.getOrientador().copiar());
       
       novo.setUsuario(this.getUsuario().copiar());
       
        for(Projeto p: this.getProjetos()){
            novo.getProjetos().add(p.copiar());
        }
        for(ProducaoAcademica p: this.getProducoes()){
            if(p instanceof Publicacao)
                novo.getProducoes().add(((Publicacao)p).copiar());
            if(p instanceof Orientacao)
                novo.getProducoes().add(((Orientacao)p).copiar());
        }
       return novo;
    }
    public Aluno copiaSimples(){
       Aluno novo = new Aluno();
       
       novo.setIdentificador(this.getIdentificador());
       novo.setNome(this.getNome());
       novo.setEmail(this.getEmail());
       novo.setDataIngresso(this.getDataIngresso());
       novo.setTipoAluno(this.getTipoAluno());
       novo.setRegimeCurso(this.getRegimeCurso());
       
       novo.setTipoProjeto(this.getTipoProjeto());
       novo.setOrientador(this.getOrientador().copiaSimples());
       
       novo.setUsuario(this.getUsuario().copiar());
       
       return novo;
    }
    
    
}
