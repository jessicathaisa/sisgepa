/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Fachada.OrientacaoFachada;
import com.jessica.Modelo.Orientacao;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class OrientacaoServlet extends ControladorCentral {

    class Retorno {
        public String comando;
        public String identificador;
        public String titulo;
        public String aluno;
        public String professor;
    }

    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Retorno obj = (Retorno) gson.fromJson(request.getReader(), Retorno.class);

        if (obj.comando != null && obj.comando.equals("listarOrientacoes")) {
            OrientacaoFachada fachada = new OrientacaoFachada();
            List<Orientacao> orientacoes = fachada.listarOrientacoes();
            String json = gson.toJson(orientacoes);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        }  else if (obj.comando != null && obj.comando.equals("buscarOrientacao")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                OrientacaoFachada fachada = new OrientacaoFachada();
                Orientacao orientacao = null;
                
                try{
                    orientacao = fachada.buscarOrientacao(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(orientacao);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}