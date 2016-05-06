/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Fachada.Relatorio;
import com.jessica.Fachada.RelatorioFachada;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class RelatorioServlet extends ControladorCentral {

    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
              
        RelatorioFachada fRel =  new RelatorioFachada();
        Relatorio relatorio = fRel.buscaRelatorio();
        Gson gson = new Gson();
        
        String json = gson.toJson(relatorio);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
