/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.ProfessorDAO;
import com.jessica.DAO.UsuarioDAO;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Modelo.Professor;
import com.jessica.Modelo.TipoUsuario;
import com.jessica.Modelo.Usuario;
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
        return dao.listar();
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
     * Buscar um professor pelo id
     * @param id
     * @return 
     */
    public Professor buscarProfessor(int id) {
        ProfessorDAO dao = new ProfessorDAO();
        return dao.buscar(id);
    }

}
