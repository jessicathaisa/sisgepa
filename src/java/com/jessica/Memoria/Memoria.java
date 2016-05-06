/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Memoria;

import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.Orientacao;
import com.jessica.Modelo.Pesquisador;
import com.jessica.Modelo.Professor;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Publicacao;
import com.jessica.Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class Memoria {
    private List<Aluno> alunos;
    private List<Professor> professores;
    private List<Pesquisador> pesquisadores;
    private List<Usuario> usuarios;
    private List<Projeto> projetos;
    private List<Publicacao> publicacoes;
    private List<Orientacao> orientacoes;
    
    private static Memoria instanciaUnica = null;

    private Memoria() {
        alunos = new ArrayList<>();
        professores = new ArrayList<>();
        pesquisadores = new ArrayList<>();
        usuarios = new ArrayList<>();
        projetos = new ArrayList<>();
        publicacoes = new ArrayList<>();
        orientacoes = new ArrayList<>();
    }

    /**
     * Pega uma instancia Singleton de Memoria.
     * 
     * @return 
     */
    public static synchronized Memoria getInstance(){
        if(instanciaUnica == null){
            instanciaUnica = new Memoria();
        }
        return instanciaUnica;
    }
    
    /**
     * @return the alunos
     */
    public List<Aluno> getAlunos() {
        return alunos;
    }

    /**
     * @param alunos the alunos to set
     */
    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    /**
     * @return the professores
     */
    public List<Professor> getProfessores() {
        return professores;
    }

    /**
     * @param professores the professores to set
     */
    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    /**
     * @return the pesquisadores
     */
    public List<Pesquisador> getPesquisadores() {
        return pesquisadores;
    }

    /**
     * @param pesquisadores the pesquisadores to set
     */
    public void setPesquisadores(List<Pesquisador> pesquisadores) {
        this.pesquisadores = pesquisadores;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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

    /**
     * @return the orientacoes
     */
    public List<Orientacao> getOrientacoes() {
        return orientacoes;
    }

    /**
     * @param orientacoes the orientacoes to set
     */
    public void setOrientacoes(List<Orientacao> orientacoes) {
        this.orientacoes = orientacoes;
    }
    
}
