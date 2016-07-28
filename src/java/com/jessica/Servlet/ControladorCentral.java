/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jessica
 */
abstract class ControladorCentral extends HttpServlet {

    protected boolean logado = false;
    
    /**
     * Processa as chamadas HTTP.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    abstract void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp); //To change body of generated methods, choose Tools | Templates.
        logado = verificaLogado(req, resp);
        processa(req, resp);
    }

    /**
     * Verifica se já existe usuário logado no sistema
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected boolean verificaLogado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession sessao = null;
        sessao = request.getSession();
        if(sessao != null){
            Boolean estaLogado = (Boolean) sessao.getAttribute("logado");
            if(estaLogado != null)
                return estaLogado;
        }
        return false;
    }
}
