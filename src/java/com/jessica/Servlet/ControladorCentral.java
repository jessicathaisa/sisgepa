/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.jessica.Fachada.Fachada;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
abstract class ControladorCentral extends HttpServlet {

    protected Fachada fachada = null;
    
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
        verificaLogado(req, resp);
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
    private boolean verificaLogado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        return false;
    }
}
