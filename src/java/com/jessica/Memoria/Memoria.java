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
import com.jessica.Modelo.RegimeCurso;
import com.jessica.Modelo.TipoAluno;
import com.jessica.Modelo.TipoUsuario;
import com.jessica.Modelo.Usuario;
import java.util.ArrayList;
import java.util.Date;
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
        
        /*Alunos*/
        Usuario usuarioa1 = new Usuario();
        usuarioa1.setLogin("aluno1");
        usuarioa1.setSenha("aluno1");
        usuarioa1.setIdentificador(5);
        usuarioa1.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa1);
        Usuario usuarioa2 = new Usuario();
        usuarioa2.setLogin("aluno2");
        usuarioa2.setSenha("aluno2");
        usuarioa2.setIdentificador(6);
        usuarioa2.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa2);
        Usuario usuarioa3 = new Usuario();
        usuarioa3.setLogin("aluno3");
        usuarioa3.setSenha("aluno3");
        usuarioa3.setIdentificador(7);
        usuarioa3.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa3);
        Usuario usuarioa4 = new Usuario();
        usuarioa4.setLogin("aluno4");
        usuarioa4.setSenha("aluno4");
        usuarioa4.setIdentificador(8);
        usuarioa4.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa4);
        Usuario usuarioa5 = new Usuario();
        usuarioa5.setLogin("aluno5");
        usuarioa5.setSenha("aluno5");
        usuarioa5.setIdentificador(9);
        usuarioa5.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa5);
        Usuario usuarioa6 = new Usuario();
        usuarioa6.setLogin("aluno6");
        usuarioa6.setSenha("aluno6");
        usuarioa6.setIdentificador(10);
        usuarioa6.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa6);
        Usuario usuarioa7 = new Usuario();
        usuarioa7.setLogin("aluno7");
        usuarioa7.setSenha("aluno7");
        usuarioa7.setIdentificador(11);
        usuarioa7.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa7);
        Usuario usuarioa8 = new Usuario();
        usuarioa8.setLogin("aluno8");
        usuarioa8.setSenha("aluno8");
        usuarioa8.setIdentificador(12);
        usuarioa8.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa8);
        Usuario usuarioa9 = new Usuario();
        usuarioa9.setLogin("aluno9");
        usuarioa9.setSenha("aluno9");
        usuarioa9.setIdentificador(13);
        usuarioa9.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioa9);
        
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Maria");
        aluno1.setEmail("maria@email.br");
        aluno1.setIdentificador(1001);
        aluno1.setDataIngresso(new Date(2006-1900,03,01));
        aluno1.setOrientador(professor1);
        aluno1.setTipoAluno(TipoAluno.GRADUACAO);
        aluno1.setRegimeCurso(RegimeCurso.NAO_SE_APLICA);
        aluno1.setUsuario(usuarioa1);
        alunos.add(aluno1);
        
        Aluno aluno2 = new Aluno();
        aluno2.setNome("João");
        aluno2.setEmail("joao@email.br");
        aluno2.setIdentificador(1002);
        aluno2.setDataIngresso(new Date(2005-1900,03,01));
        aluno2.setOrientador(professor2);
        aluno2.setTipoAluno(TipoAluno.GRADUACAO);
        aluno2.setRegimeCurso(RegimeCurso.NAO_SE_APLICA);
        aluno2.setUsuario(usuarioa2);
        alunos.add(aluno2);
        
        Aluno aluno3 = new Aluno();
        aluno3.setNome("Mário");
        aluno3.setEmail("mario@email.br");
        aluno3.setIdentificador(1003);
        aluno3.setDataIngresso(new Date(2007-1900,03,01));
        aluno3.setOrientador(professor2);
        aluno3.setTipoAluno(TipoAluno.GRADUACAO);
        aluno3.setRegimeCurso(RegimeCurso.NAO_SE_APLICA);
        aluno3.setUsuario(usuarioa3);
        alunos.add(aluno3);
        
        Aluno aluno4 = new Aluno();
        aluno4.setNome("Soraia");
        aluno4.setEmail("soraia@email.br");
        aluno4.setIdentificador(1004);
        aluno4.setDataIngresso(new Date(2006-1900,03,01));
        aluno4.setOrientador(professor1);
        aluno4.setTipoAluno(TipoAluno.MESTRADO);
        aluno4.setRegimeCurso(RegimeCurso.PARCIAL);
        aluno4.setUsuario(usuarioa4);
        alunos.add(aluno4);
        
        Aluno aluno5 = new Aluno();
        aluno5.setNome("Rafael");
        aluno5.setEmail("rafael@email.br");
        aluno5.setIdentificador(1005);
        aluno5.setDataIngresso(new Date(2007-1900,06,01));
        aluno5.setOrientador(professor3);
        aluno5.setTipoAluno(TipoAluno.MESTRADO);
        aluno5.setRegimeCurso(RegimeCurso.PARCIAL);
        aluno5.setUsuario(usuarioa5);
        alunos.add(aluno5);
        
        Aluno aluno6 = new Aluno();
        aluno6.setNome("Marta");
        aluno6.setEmail("marta@email.br");
        aluno6.setIdentificador(1006);
        aluno6.setDataIngresso(new Date(2007-1900,06,01));
        aluno6.setOrientador(professor3);
        aluno6.setTipoAluno(TipoAluno.MESTRADO);
        aluno6.setRegimeCurso(RegimeCurso.INTEGRAL);
        aluno6.setUsuario(usuarioa6);
        alunos.add(aluno6);
        
        Aluno aluno7 = new Aluno();
        aluno7.setNome("Daniel");
        aluno7.setEmail("daniel@email.br");
        aluno7.setIdentificador(1007);
        aluno7.setDataIngresso(new Date(2006-1900,03,01));
        aluno7.setOrientador(professor3);
        aluno7.setTipoAluno(TipoAluno.MESTRADO);
        aluno7.setRegimeCurso(RegimeCurso.INTEGRAL);
        aluno7.setUsuario(usuarioa7);
        alunos.add(aluno7);
        
        Aluno aluno8 = new Aluno();
        aluno8.setNome("Michael");
        aluno8.setEmail("michael@email.br");
        aluno8.setIdentificador(1008);
        aluno8.setDataIngresso(new Date(2005-1900,03,01));
        aluno8.setOrientador(professor1);
        aluno8.setTipoAluno(TipoAluno.DOUTORADO);
        aluno8.setRegimeCurso(RegimeCurso.INTEGRAL);
        aluno8.setUsuario(usuarioa8);
        alunos.add(aluno8);
        
        Aluno aluno9 = new Aluno();
        aluno9.setNome("Bia");
        aluno9.setEmail("bia@mail.br");
        aluno9.setIdentificador(1009);
        aluno9.setDataIngresso(new Date(2004-1900,06,01));
        aluno9.setOrientador(professor1);
        aluno9.setTipoAluno(TipoAluno.DOUTORADO);
        aluno9.setRegimeCurso(RegimeCurso.INTEGRAL);
        aluno9.setUsuario(usuarioa9);
        alunos.add(aluno9);
        
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
