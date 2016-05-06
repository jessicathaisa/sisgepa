/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.TipoUsuario;
import com.jessica.Modelo.Usuario;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class UsuarioDAO extends DAO {

    /**
     * Adiciona Usuario na memoria
     *
     * @param login
     * @param senha
     * @param tipo
     * @return
     */
    public Usuario addUsuario(String login, String senha, TipoUsuario tipo) {
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setTipo(tipo);

        try {
            List<Usuario> usuarios = memoria.getUsuarios();
            int id = 1;
            if(usuarios.size() != 0)
                id = usuarios.get(usuarios.size() - 1).getIdentificador();
            usuario.setIdentificador(id);

            memoria.getUsuarios().add(usuario);
        } catch (OutOfMemoryError oomex) {
            return null;
        }

        return usuario;
    }

    /**
     * Remover Usuario pelo login
     * @param login
     * @return 
     */
    public boolean remUsuario(String login) {
        List<Usuario> usuarios = memoria.getUsuarios();
        Usuario obj = null;
        for (Usuario user : usuarios) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                obj = user;
            }
        }
        try {
            memoria.getUsuarios().remove(obj);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * Remover Usuario pelo identificador
     * @param id
     * @return 
     */
    public boolean remUsuario(int id) {
        List<Usuario> usuarios = memoria.getUsuarios();

        for (Usuario user : usuarios) {
            if (user.getIdentificador() == id) {
                try {
                    memoria.getUsuarios().remove(user);
                } catch (Exception ex) {
                    return false;
                }
                return true;
            }
        }

        return true;
    }

    /**
     * Busca Usuário por identificador
     * @param id
     * @return 
     */
    public Usuario buscar(int id) {
        List<Usuario> usuarios = memoria.getUsuarios();
        for (Usuario user : usuarios) {
            if (user.getIdentificador() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Busca Usuário por login
     * @param login
     * @return 
     */
    public Usuario buscar(String login) {
        List<Usuario> usuarios = memoria.getUsuarios();
        for (Usuario user : usuarios) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Busca Usuário por login e senha
     * @param login
     * @param senha
     * @return 
     */
    public Usuario buscar(String login, String senha) {
        List<Usuario> usuarios = memoria.getUsuarios();
        for (Usuario user : usuarios) {
            if (user.getLogin().equalsIgnoreCase(login) && user.getSenha().equals(senha)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Altera a senha do usuário com o login informado para a senha informada
     * @param login Login do usuário cuja senha mudará
     * @param senha Senha nova
     * @return 
     */
    public boolean alteraSenha(String login, String senha){
        List<Usuario> usuarios = memoria.getUsuarios();

        for (Usuario user : usuarios) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                try {
                    user.setSenha(senha);
                } catch (Exception ex) {
                    return false;
                }
                return true;
            }
        }

        return true;
    }
}
