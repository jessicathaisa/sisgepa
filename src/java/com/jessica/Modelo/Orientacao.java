/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Modelo;

/**
 *
 * @author Jessica
 */
public class Orientacao extends ProducaoAcademica {

    private Aluno aluno;
    private Professor professor;

    /**
     * @return the aluno
     */
    public Aluno getAluno() {
        return aluno;
    }

    /**
     * @param aluno the aluno to set
     */
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    /**
     * @return the professor
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * @param professor the professor to set
     */
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    /**
     * Realiza uma cópia do objeto
     * @return 
     */
    public Orientacao copiar(){
        Orientacao novo = new Orientacao();
        
        Aluno aluno = this.getAluno().copiar();
        Professor professor = this.getProfessor().copiar();
        
        novo.setAluno(aluno);
        novo.setProfessor(professor);
        novo.setIdentificador(this.getIdentificador());
        novo.setTitulo(this.getTitulo());
        novo.setAno(this.getAno());
        
        return novo;
    }

    /**
     * Realiza uma cópia do objeto evitando erros de referencias circulares
     * @return 
     */
    public Orientacao copiaSimples(){
        Orientacao novo = new Orientacao();
        
        Aluno aluno = this.getAluno().copiaSimples();
        Professor professor = this.getProfessor().copiaSimples();
        
        novo.setAluno(aluno);
        novo.setProfessor(professor);
        novo.setIdentificador(this.getIdentificador());
        novo.setTitulo(this.getTitulo());
        novo.setAno(this.getAno());
        
        return novo;
    }
}
