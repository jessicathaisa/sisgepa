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
import com.jessica.Modelo.StatusProjeto;
import com.jessica.Modelo.TipoAluno;
import com.jessica.Modelo.TipoProjetoAluno;
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
        aluno1.setTipoProjeto(TipoProjetoAluno.TRABALHO_CONCLUSAO_CURSO);
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
        aluno2.setTipoProjeto(TipoProjetoAluno.TRABALHO_CONCLUSAO_CURSO);
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
        aluno3.setTipoProjeto(TipoProjetoAluno.TRABALHO_CONCLUSAO_CURSO);
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
        aluno4.setTipoProjeto(TipoProjetoAluno.DISSERTACAO);
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
        aluno5.setTipoProjeto(TipoProjetoAluno.DISSERTACAO);
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
        aluno6.setTipoProjeto(TipoProjetoAluno.DISSERTACAO);
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
        aluno7.setTipoProjeto(TipoProjetoAluno.DISSERTACAO);
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
        aluno8.setTipoProjeto(TipoProjetoAluno.TESE);
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
        aluno9.setTipoProjeto(TipoProjetoAluno.TESE);
        aluno9.setUsuario(usuarioa9);
        alunos.add(aluno9);
        
        
        /* Projetos */        
        Projeto projeto1 = new Projeto();
        projeto1.setIdentificador(20);
        projeto1.setTitulo("Engenharia de Software para Sistemas Multi-Agentes (ESMA)");
        projeto1.setDataInicio(new Date(2003-1900, 2, 2));
        projeto1.setDataTermino(new Date(2010-1900, 2, 2));
        projeto1.setAgenciaFinanciadora("FPCL");
        projeto1.setValorFinanciado(300000);
        projeto1.setObjetivo("O objetivo geral deste projeto é desenvolver os fundamentos e as tecnologias da ESSMA.");
        projeto1.setDescricao("Pesquisar, aplicar e avaliar técnicas de desenvolvimento de software para sistemas multi-agentes..");
        projeto1.getParticipantes().add(professor1);
        projeto1.getParticipantes().add(professor2);
        projeto1.getParticipantes().add(aluno2);
        projeto1.getParticipantes().add(aluno3);
        projeto1.getParticipantes().add(aluno5);
        projeto1.getParticipantes().add(aluno6);
        projeto1.getParticipantes().add(aluno7);
        projeto1.getParticipantes().add(aluno8);
        projeto1.getParticipantes().add(aluno9);
        projeto1.setStatus(StatusProjeto.EM_ANDAMENTO);
        professor1.getProjetos().add(projeto1);
        professor2.getProjetos().add(projeto1);
        aluno2.getProjetos().add(projeto1);
        aluno3.getProjetos().add(projeto1);
        aluno5.getProjetos().add(projeto1);
        aluno6.getProjetos().add(projeto1);
        aluno7.getProjetos().add(projeto1);
        aluno8.getProjetos().add(projeto1);
        aluno9.getProjetos().add(projeto1);
        projetos.add(projeto1);
        
        Projeto projeto2 = new Projeto();
        projeto2.setIdentificador(30);
        projeto2.setTitulo("Engenharia de Software Orientada a Aspectos (ESOA) ");
        projeto2.setDataInicio(new Date(2005-1900, 2, 2));
        projeto2.setDataTermino(new Date(2011-1900, 12, 2));
        projeto2.setAgenciaFinanciadora("FPCL");
        projeto2.setValorFinanciado(190000);
        projeto2.setObjetivo("O objetivo geral deste projeto é desenvolver os fundamentos e as tecnologias da ESOA. ");
        projeto2.setDescricao("Pesquisar, aplicar e avaliar técnicas de desenvolvimento de software orientado à aspectos.. ");
        projeto2.getParticipantes().add(professor1);
        projeto2.getParticipantes().add(professor2);
        projeto2.getParticipantes().add(aluno1);
        projeto2.getParticipantes().add(aluno2);
        projeto2.getParticipantes().add(aluno4);
        projeto2.getParticipantes().add(aluno7);
        projeto2.getParticipantes().add(aluno8);
        projeto2.setStatus(StatusProjeto.CONCLUIDO);
        professor1.getProjetos().add(projeto2);
        professor2.getProjetos().add(projeto2);
        aluno1.getProjetos().add(projeto2);
        aluno2.getProjetos().add(projeto2);
        aluno4.getProjetos().add(projeto2);
        aluno7.getProjetos().add(projeto2);
        aluno8.getProjetos().add(projeto2);
        projetos.add(projeto2);
        
        Projeto projeto3 = new Projeto();
        projeto3.setIdentificador(40);
        projeto3.setTitulo("Qualidade de Software");
        projeto3.setDataInicio(new Date(2006-1900, 5, 2));
        projeto3.setDataTermino(new Date(2009-1900, 10, 2));
        projeto3.setAgenciaFinanciadora("FPCL");
        projeto3.setValorFinanciado(100000);
        projeto3.setObjetivo("O objetivo deste projeto é desenvolver os fundamentos e as tecnologias para desenvolvimento de software com qualidade.");
        projeto3.setDescricao("Pesquisar, aplicar e avaliar técnicas para qualidade em desenvolvimento de software. ");
        projeto3.getParticipantes().add(professor3);
        projeto3.getParticipantes().add(professor2);
        projeto3.getParticipantes().add(aluno1);
        projeto3.getParticipantes().add(aluno3);
        projeto3.getParticipantes().add(aluno5);
        projeto3.getParticipantes().add(aluno6);
        projeto3.getParticipantes().add(aluno7);
        projeto3.getParticipantes().add(aluno9);
        projeto3.setStatus(StatusProjeto.EM_ELABORACAO);
        professor3.getProjetos().add(projeto3);
        professor2.getProjetos().add(projeto3);
        aluno1.getProjetos().add(projeto3);
        aluno3.getProjetos().add(projeto3);
        aluno5.getProjetos().add(projeto3);
        aluno6.getProjetos().add(projeto3);
        aluno7.getProjetos().add(projeto3);
        aluno9.getProjetos().add(projeto3);
        projetos.add(projeto3);
        
        Projeto projeto4 = new Projeto();
        projeto4.setIdentificador(50);
        projeto4.setTitulo("Model-driven Software Product Lines Development");
        projeto4.setDataInicio(null);
        projeto4.setDataTermino(null);
        projeto4.setAgenciaFinanciadora("FPCL");
        projeto4.setValorFinanciado(500000);
        projeto4.setObjetivo("O objetivo deste projeto é elaborar técnicas de engenharia de software dirigadas a modelos para o desenvolvimento de linhas de produtos de software.  ");
        projeto4.setDescricao("Pesquisar, aplicar e avaliar técnicas para o desenvolvimento de linhas de produtos de software. ");
        projeto4.getParticipantes().add(professor3);
        projeto4.getParticipantes().add(professor2);
        projeto4.getParticipantes().add(aluno1);
        projeto4.getParticipantes().add(aluno3);
        projeto4.getParticipantes().add(aluno5);
        projeto4.getParticipantes().add(aluno6);
        projeto4.getParticipantes().add(aluno7);
        projeto4.getParticipantes().add(aluno9);
        projeto4.setStatus(StatusProjeto.EM_ELABORACAO);
        professor3.getProjetos().add(projeto4);
        professor2.getProjetos().add(projeto4);
        aluno1.getProjetos().add(projeto4);
        aluno3.getProjetos().add(projeto4);
        aluno5.getProjetos().add(projeto4);
        aluno6.getProjetos().add(projeto4);
        aluno7.getProjetos().add(projeto4);
        aluno9.getProjetos().add(projeto4);
        projetos.add(projeto4);
        
        Projeto projeto5 = new Projeto();
        projeto5.setIdentificador(60);
        projeto5.setTitulo("Self-organizing Multi-agent Systems");
        projeto5.setDataInicio(new Date(2008-1900, 7, 15));
        projeto5.setDataTermino(new Date(2010-1900, 10, 2));
        projeto5.setAgenciaFinanciadora("FPCL");
        projeto5.setValorFinanciado(150000);
        projeto5.setObjetivo("O objetivo deste projeto é desenvolver sistemas multi-agentes autoorganizáveis.");
        projeto5.setDescricao("Pesquisar, aplicar e avaliar técnicas para o desenvolvimento de sistemas multi-agentes auto-organizáveis. ");
        projeto5.getParticipantes().add(aluno1);
        projeto5.getParticipantes().add(aluno3);
        projeto5.getParticipantes().add(aluno5);
        projeto5.getParticipantes().add(aluno6);
        projeto5.getParticipantes().add(aluno7);
        projeto5.getParticipantes().add(aluno9);
        projeto5.setStatus(StatusProjeto.EM_ELABORACAO);
        aluno1.getProjetos().add(projeto5);
        aluno3.getProjetos().add(projeto5);
        aluno5.getProjetos().add(projeto5);
        aluno6.getProjetos().add(projeto5);
        aluno7.getProjetos().add(projeto5);
        aluno9.getProjetos().add(projeto5);
        projetos.add(projeto5);
        
        
        /*Pesquisadores*/
        Usuario usuarioq1 = new Usuario();
        usuarioq1.setLogin("pesquisador1");
        usuarioq1.setSenha("pesquisador1");
        usuarioq1.setIdentificador(2);
        usuarioq1.setTipo(TipoUsuario.GERENTE);
        usuarios.add(usuarioq1);
        Usuario usuarioq2 = new Usuario();
        usuarioq2.setLogin("pesquisador2");
        usuarioq2.setSenha("pesquisador2");
        usuarioq2.setIdentificador(3);
        usuarioq2.setTipo(TipoUsuario.GERENTE);
        usuarios.add(usuarioq2);
        Usuario usuarioq3 = new Usuario();
        usuarioq3.setLogin("pesquisador3");
        usuarioq3.setSenha("pesquisador3");
        usuarioq3.setIdentificador(4);
        usuarioq3.setTipo(TipoUsuario.COLABORADOR);
        usuarios.add(usuarioq3);
        
        Pesquisador pesquisador1 = new Pesquisador();
        pesquisador1.setNome("Pesq. Jacob");
        pesquisador1.setEmail("jacob@email.br");
        pesquisador1.setIdentificador(10000);
        pesquisador1.setUsuario(usuarioq1);
        pesquisadores.add(pesquisador1);
        
        Pesquisador pesquisador2 = new Pesquisador();
        pesquisador2.setNome("Pesq. Julian");
        pesquisador2.setEmail("julian@email.br");
        pesquisador2.setIdentificador(10001);
        pesquisador2.setUsuario(usuarioq2);
        pesquisadores.add(pesquisador2);
        
        Pesquisador pesquisador3 = new Pesquisador();
        pesquisador3.setNome("Pesq. Martina");
        pesquisador3.setEmail("martina@email.br");
        pesquisador3.setIdentificador(10002);
        pesquisador3.setUsuario(usuarioq3);
        pesquisadores.add(pesquisador3);
        
        
        /* Orientações */
        Orientacao orientacao1 = new Orientacao();
        orientacao1.setProfessor(professor1);
        orientacao1.setAluno(aluno1);
        orientacao1.setTitulo("Usabilidade no Portal do Banco do Brasil");
        orientacao1.setIdentificador(1);
        orientacao1.setAno(2005);
        orientacoes.add(orientacao1);
        aluno1.getProducoes().add(orientacao1);
        professor1.getProducoes().add(orientacao1);
        
        Orientacao orientacao2 = new Orientacao();
        orientacao2.setProfessor(professor1);
        orientacao2.setAluno(aluno4);
        orientacao2.setTitulo("Framework para o Cálculo de Reputação de Agentes");
        orientacao2.setIdentificador(2);
        orientacao2.setAno(2008);
        orientacoes.add(orientacao2);
        aluno4.getProducoes().add(orientacao2);
        professor1.getProducoes().add(orientacao2);
        
        Orientacao orientacao3 = new Orientacao();
        orientacao3.setProfessor(professor3);
        orientacao3.setAluno(aluno5);
        orientacao3.setTitulo("Arquitetura para catálogos de Objetos baseado em Ontologias");
        orientacao3.setIdentificador(3);
        orientacao3.setAno(2007);
        orientacoes.add(orientacao3);
        aluno5.getProducoes().add(orientacao3);
        professor3.getProducoes().add(orientacao3);
        
        Orientacao orientacao4 = new Orientacao();
        orientacao4.setProfessor(professor3);
        orientacao4.setAluno(aluno7);
        orientacao4.setTitulo("Framework para Smart Cards");
        orientacao4.setIdentificador(4);
        orientacao4.setAno(2012);
        orientacoes.add(orientacao4);
        aluno7.getProducoes().add(orientacao4);
        professor3.getProducoes().add(orientacao4);
        
        Orientacao orientacao5 = new Orientacao();
        orientacao5.setProfessor(professor1);
        orientacao5.setAluno(aluno9);
        orientacao5.setTitulo("Linguagem de modelagem para Sistemas baseados em Agentes");
        orientacao5.setIdentificador(5);
        orientacao5.setAno(2000);
        orientacoes.add(orientacao5);
        aluno9.getProducoes().add(orientacao5);
        professor1.getProducoes().add(orientacao5);
        
        
        /* Publicações */
        Publicacao publicacao1 = new Publicacao();
        publicacao1.setTitulo("Abordagem Quantitativa para Desenvolvimento de Software Orientado a Aspectos");
        publicacao1.getAutores().add(aluno4);
        publicacao1.getAutores().add(aluno8);
        publicacao1.getAutores().add(professor1);
        publicacao1.setConferencia("SBQS");
        publicacao1.setAno(2006);
        publicacao1.setProjeto(projeto2);
        publicacao1.setIdentificador(1);
        publicacoes.add(publicacao1);
        aluno4.getProducoes().add(publicacao1);
        aluno8.getProducoes().add(publicacao1);
        professor1.getProducoes().add(publicacao1);
        
        Publicacao publicacao2 = new Publicacao();
        publicacao2.setTitulo("Refactoring Product Lines");
        publicacao2.getAutores().add(aluno1);
        publicacao2.getAutores().add(aluno9);
        publicacao2.getAutores().add(professor2);
        publicacao2.setConferencia("GPCE");
        publicacao2.setAno(2007);
        publicacao2.setProjeto(null);
        publicacao2.setIdentificador(2);
        publicacoes.add(publicacao2);
        aluno1.getProducoes().add(publicacao2);
        aluno9.getProducoes().add(publicacao2);
        professor2.getProducoes().add(publicacao2);
        
        Publicacao publicacao3 = new Publicacao();
        publicacao3.setTitulo("Tratamento de Exceções Sensível ao Contexto");
        publicacao3.getAutores().add(aluno6);
        publicacao3.getAutores().add(professor3);
        publicacao3.setConferencia("SBES");
        publicacao3.setAno(2006);
        publicacao3.setProjeto(null);
        publicacao3.setIdentificador(3);
        publicacoes.add(publicacao3);
        aluno6.getProducoes().add(publicacao3);
        professor3.getProducoes().add(publicacao3);
        
        Publicacao publicacao4 = new Publicacao();
        publicacao4.setTitulo("Integrating MAS in component-based groupware environment");
        publicacao4.getAutores().add(aluno6);
        publicacao4.getAutores().add(aluno7);
        publicacao4.getAutores().add(professor3);
        publicacao4.setConferencia("AOSE");
        publicacao4.setAno(2006);
        publicacao4.setProjeto(projeto1);
        publicacao4.setIdentificador(4);
        publicacoes.add(publicacao4);
        aluno6.getProducoes().add(publicacao4);
        aluno7.getProducoes().add(publicacao4);
        professor3.getProducoes().add(publicacao4);
        
        Publicacao publicacao5 = new Publicacao();
        publicacao5.setTitulo("Reputing Model Based on Testimonies");
        publicacao5.getAutores().add(aluno1);
        publicacao5.getAutores().add(aluno9);
        publicacao5.getAutores().add(professor1);
        publicacao5.setConferencia("AAMAS");
        publicacao5.setAno(2006);
        publicacao5.setProjeto(projeto1);
        publicacao5.setIdentificador(5);
        publicacoes.add(publicacao5);
        aluno1.getProducoes().add(publicacao5);
        aluno9.getProducoes().add(publicacao5);
        professor1.getProducoes().add(publicacao5);
        
        Publicacao publicacao6 = new Publicacao();
        publicacao6.setTitulo("Extensions on Interacrion Laws in Open Multi-Agent Systems");
        publicacao6.getAutores().add(aluno8);
        publicacao6.setConferencia("SEAS");
        publicacao6.setAno(2005);
        publicacao6.setProjeto(null);
        publicacao6.setIdentificador(6);
        publicacoes.add(publicacao6);
        aluno8.getProducoes().add(publicacao6);
        
        Publicacao publicacao7 = new Publicacao();
        publicacao7.setTitulo("Aspect-oriented Patterns");
        publicacao7.getAutores().add(aluno4);
        publicacao7.getAutores().add(professor1);
        publicacao7.setConferencia("FLOP");
        publicacao7.setAno(2006);
        publicacao7.setProjeto(projeto2);
        publicacao7.setIdentificador(7);
        publicacoes.add(publicacao7);
        aluno4.getProducoes().add(publicacao7);
        professor1.getProducoes().add(publicacao7);
        
        Publicacao publicacao8 = new Publicacao();
        publicacao8.setTitulo("Classifying an Describing Agent Cintracts and Norms");
        publicacao8.getAutores().add(aluno2);
        publicacao8.getAutores().add(aluno7);
        publicacao8.setConferencia("AAMAS");
        publicacao8.setAno(2005);
        publicacao8.setProjeto(projeto1);
        publicacao8.setIdentificador(8);
        publicacoes.add(publicacao8);
        aluno2.getProducoes().add(publicacao8);
        aluno7.getProducoes().add(publicacao8);
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
