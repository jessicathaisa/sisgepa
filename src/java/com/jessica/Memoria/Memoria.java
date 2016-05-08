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
import com.jessica.Modelo.TipoUsuario;
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
        
        /*Administrador Inicial*/
        Usuario usuario = new Usuario();
        usuario.setLogin("jessica");
        usuario.setSenha("jessica");
        usuario.setIdentificador(1);
        usuario.setTipo(TipoUsuario.ADMINISTRADOR);
        usuarios.add(usuario);
        
        /*Professores*/
        Usuario usuariop1 = new Usuario();
        usuariop1.setLogin("professor1");
        usuariop1.setSenha("professor1");
        usuariop1.setIdentificador(2);
        usuariop1.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuariop1);
        Usuario usuariop2 = new Usuario();
        usuariop2.setLogin("professor2");
        usuariop2.setSenha("professor2");
        usuariop2.setIdentificador(3);
        usuariop2.setTipo(TipoUsuario.GERENTE);
        usuarios.add(usuariop2);
        Usuario usuariop3 = new Usuario();
        usuariop3.setLogin("professor3");
        usuariop3.setSenha("professor3");
        usuariop3.setIdentificador(4);
        usuariop3.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuariop3);
        
        Professor professor1 = new Professor();
        professor1.setNome("Prof. Carlos");
        professor1.setEmail("carlos@email.br");
        professor1.setIdentificador(100);
        professor1.setUsuario(usuariop1);
        professores.add(professor1);
        
        Professor professor2 = new Professor();
        professor2.setNome("Prof. Arnaldo");
        professor2.setEmail("arnaldo@email.br");
        professor2.setIdentificador(101);
        professor2.setUsuario(usuariop2);
        professores.add(professor2);
        
        Professor professor3 = new Professor();
        professor3.setNome("Prof. Paulo");
        professor3.setEmail("paulo@email.br");
        professor3.setIdentificador(102);
        professor3.setUsuario(usuariop3);
        professores.add(professor3);
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
