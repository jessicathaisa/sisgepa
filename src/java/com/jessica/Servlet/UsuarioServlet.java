/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Fachada.UsuarioFachada;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class UsuarioServlet extends ControladorCentral {

    class Retorno {
        public String usuario;
        public String senha;
        public String comando;
    }
    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Retorno obj = (Retorno) gson.fromJson(request.getReader(), Retorno.class );
        
        UsuarioFachada usuarioFachada = new UsuarioFachada();
        
        if(obj.comando != null && obj.comando.equals("realizarLogin")){
            boolean conseguiuLogin = usuarioFachada.verificarPermissaoAcesso(obj.usuario, obj.senha);
            
            if(!conseguiuLogin)
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            else{
                response.setStatus(HttpServletResponse.SC_OK);
                request.getSession(true);
                request.getSession().setAttribute("logado", true);
            }
        }
        else if(obj.comando != null && obj.comando.equals("verificaLogado")){
            if(logado)
                response.setStatus(HttpServletResponse.SC_OK);
            else
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}
