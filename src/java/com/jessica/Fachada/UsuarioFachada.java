/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.UsuarioDAO;
import com.jessica.Modelo.Usuario;

/**
 *
 * @author Jessica
 */
public class UsuarioFachada extends Fachada{
    
    /**
     * Verifica se o usuário informado possui permissão de acesso com a sneha informada
     * @param login
     * @param senha
     * @return 
     */
    public boolean verificarPermissaoAcesso(String login, String senha){
        UsuarioDAO dao = new UsuarioDAO();
        
        Usuario usuario = dao.buscar(login, senha);
        
        return usuario != null;
    }
}
