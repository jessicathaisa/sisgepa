/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jessica.Modelo;

/**
 *
 * @author Jessica
 */
public class Usuario {
    private int identificador;
    private String login;
    private String senha;
    private TipoUsuario tipo;

    /**
     * @return the identificador
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the tipo
     */
    public TipoUsuario getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Realiza uma cópia do objeto
     * @return 
     */
    public Usuario copiar(){
        Usuario novo = new Usuario();
        novo.setIdentificador(this.identificador);
        novo.setLogin(this.login);
        novo.setSenha(this.senha);
        novo.setTipo(this.tipo);
        
        return novo;
    }
}
