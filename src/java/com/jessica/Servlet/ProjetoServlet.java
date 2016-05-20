/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Fachada.ProjetoFachada;
import com.jessica.Modelo.Projeto;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class ProjetoServlet extends ControladorCentral {

    class Retorno {
        public String comando;
        public String identificador;
        public String titulo;
    }
    
    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        OrientacaoServlet.Retorno obj = (OrientacaoServlet.Retorno) gson.fromJson(request.getReader(), OrientacaoServlet.Retorno.class);

        if (obj.comando != null && obj.comando.equals("listarProjetosEmAndamento")) {
            ProjetoFachada fachada = new ProjetoFachada();
            List<Projeto> projetos = fachada.listarEmAndamento();
            String json = gson.toJson(projetos);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (obj.comando != null && obj.comando.equals("listarProjetos")) {
            ProjetoFachada fachada = new ProjetoFachada();
            List<Projeto> projetos = fachada.listar();
            String json = gson.toJson(projetos);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (obj.comando != null && obj.comando.equals("buscarProjeto")) {
            ProjetoFachada fachada = new ProjetoFachada();
            Projeto projeto = fachada.buscar(Integer.parseInt(obj.identificador));
            String json = gson.toJson(projeto);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
