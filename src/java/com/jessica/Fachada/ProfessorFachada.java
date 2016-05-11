/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.OrientacaoDAO;
import com.jessica.DAO.ProfessorDAO;
import com.jessica.DAO.ProjetoDAO;
import com.jessica.DAO.PublicacaoDAO;
import com.jessica.DAO.UsuarioDAO;
import com.jessica.Excecao.PossuiOrientandosException;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.Orientacao;
import com.jessica.Modelo.ProducaoAcademica;
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
public class ProfessorFachada extends Fachada{
    /**
     * Lista os professores existentes no sistema
     * @return 
     */
    public List<Professor> listarProfessores(){
        ProfessorDAO dao = new ProfessorDAO();

        List<Professor> lista = dao.listar();
        List<Professor> listaAuxiliar = new ArrayList<>();

        if (lista != null) {
            for (Professor pesq : lista) {
                Professor a = pesq.copiar();
                if (a.getProjetos() != null) {
                    for (Projeto p : a.getProjetos()) {
                        p.setParticipantes(null);
                    }
                }
                if (a.getProducoes() != null) {
                    for (ProducaoAcademica p : a.getProducoes()) {
                        if (p instanceof Publicacao) {
                            ((Publicacao) p).setAutores(null);
                        }
                    }
                }
                listaAuxiliar.add(a);
            }
        }

        return listaAuxiliar;
    }
    
    /**
     * Cadastrar o professor no sistema
     * @param nome
     * @param email
     * @param loginUsuario
     * @param senhaUsuario
     * @param tipoUsuario
     * @return
     * @throws UsuarioDuplicadoException 
     */
    public Professor cadastrarProfessor(String nome, String email, String loginUsuario, String senhaUsuario, String tipoUsuario) throws UsuarioDuplicadoException {
        Professor professor = null;
        ProfessorDAO dao = new ProfessorDAO();
        UsuarioDAO usdao = new UsuarioDAO();
        Usuario usuario = null;

        // Verifica se o usuário já existe no sistema
        usuario = usdao.buscar(loginUsuario);
        if (usuario != null) {
            throw new UsuarioDuplicadoException();
        }

        // Tenta parsear os dados vindos do cliente
        try {
            TipoUsuario tipoUser = TipoUsuario.valueOf(tipoUsuario);
            usuario = usdao.addUsuario(loginUsuario, senhaUsuario, tipoUser);
            
            professor = dao.addProfessor(nome, email, usuario.getIdentificador());

        } catch (IllegalArgumentException ilg) {
            if (usuario != null) {
                usdao.remUsuario(usuario.getIdentificador());
            }
            return null;
        }

        return professor;
    }

    /**
     * Editar dados do professor
     * @param id
     * @param nome
     * @param email
     * @param loginUsuario
     * @param senhaUsuario
     * @param tipoUsuario
     * @return
     * @throws UsuarioDuplicadoException 
     */
    public Professor editarProfessor(int id, String nome, String email, String loginUsuario, String senhaUsuario, String tipoUsuario) throws UsuarioDuplicadoException {
        Professor professor = null;
        ProfessorDAO dao = new ProfessorDAO();
        UsuarioDAO usdao = new UsuarioDAO();
        Usuario usuario = null;
        professor = dao.buscar(id);
        Usuario usuarioprofessor = professor.getUsuario();

        if (loginUsuario.equals(usuarioprofessor.getLogin())) {
            // Não quero mudar.
            usuario = usuarioprofessor;
        } else {
            // Verifica se o usuário já existe no sistema
            usuario = usdao.buscar(loginUsuario);
            if (usuario != null) {
                throw new UsuarioDuplicadoException();
            } else {
                usdao.remUsuario(usuarioprofessor.getIdentificador());
                TipoUsuario tipoUser = TipoUsuario.valueOf(tipoUsuario);
                usuario = usdao.addUsuario(loginUsuario, senhaUsuario, tipoUser);
            }
        }
        
        // Tenta parsear os dados vindos do cliente
        try {
            professor = dao.editarProfessor(id, nome, email);
            professor.setUsuario(usuario);

        } catch (IllegalArgumentException ilg) {
            if (usuario != null) {
                usdao.remUsuario(usuario.getIdentificador());
            }
            return null;
        }

        return professor;
    }
    
    /**
     * Apagar os dados do professor
     * @param id
     * @return 
     * @throws com.jessica.Excecao.PossuiOrientandosException 
     */
    public boolean apagarProfessor(int id) throws PossuiOrientandosException{
        ProfessorDAO dao = new ProfessorDAO();
        ProjetoDAO projdao = new ProjetoDAO();
        PublicacaoDAO pubdao = new PublicacaoDAO();
        OrientacaoDAO oridao = new OrientacaoDAO();
        
        AlunoFachada af = new AlunoFachada();
        
        try{
            Professor professor = dao.buscar(id);
            
            boolean existemAlunos = false;
            for(Aluno a : af.listarAlunos()){
                if(a.getOrientador().getIdentificador() == professor.getIdentificador()){
                    existemAlunos = true;
                    break;
                }
            }
            if(existemAlunos)
                throw new PossuiOrientandosException();
            
            /*Remove dos projetos em que participa*/
            for(Projeto projeto : professor.getProjetos()){
                projdao.remParticipante(projeto.getIdentificador(), professor.getIdentificador());
            }
            
            for(ProducaoAcademica producao : professor.getProducoes()){
                if(producao instanceof Publicacao)
                    pubdao.remAutor(producao.getIdentificador(), professor.getIdentificador());
                else if(producao instanceof Orientacao){
                    Orientacao orientacao = (Orientacao) producao;
                    oridao.remOrientacao(orientacao.getAluno().getIdentificador(), orientacao.getProfessor().getIdentificador());
                }
            }
            
            dao.remProfessor(id);
        } catch (PossuiOrientandosException pex){
            throw pex;
        } catch(Exception ex){
            return false;
        }
        return true;
    }

    /**
     * Buscar um professor pelo id
     * @param id
     * @return 
     */
    public Professor buscarProfessor(int id) {        
        ProfessorDAO dao = new ProfessorDAO();
        Professor a = dao.buscar(id).copiar();
        if (a.getProjetos() != null) {
            for (Projeto p : a.getProjetos()) {
                p.setParticipantes(null);
            }
        }
        if (a.getProducoes() != null) {
            for (ProducaoAcademica p : a.getProducoes()) {
                if (p instanceof Publicacao) {
                    ((Publicacao) p).setAutores(null);
                }
            }
        }
        return a;
    }

}
