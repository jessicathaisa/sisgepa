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
import java.text.SimpleDateFormat;
import java.util.Date;
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
        public String dataInicio;
        public String dataTermino;
        public String agenciaFinanciadora;
        public String valorFinanciado;
        public String objetivo;
        public String descricao;
    }
    
    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Retorno obj = (Retorno) gson.fromJson(request.getReader(), Retorno.class);

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
        } else if (obj.comando != null && obj.comando.equals("cadastrarProjeto")) {
            ProjetoFachada fachada = new ProjetoFachada();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            try {
                Date dataInicio = obj.dataInicio != null && !"".equals(obj.dataInicio) ? sdf.parse(obj.dataInicio) : null;
                Date dataTermino = obj.dataTermino != null && !"".equals(obj.dataTermino) ? sdf.parse(obj.dataTermino) : null;
                Float valor = obj.valorFinanciado != null && !"".equals(obj.valorFinanciado) ? Float.parseFloat(obj.valorFinanciado) : 0;
                Projeto projeto = fachada.cadastrarProjeto(obj.titulo, dataInicio, dataTermino, obj.agenciaFinanciadora, valor, obj.objetivo, obj.descricao);

                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("editarProjeto")) {
            ProjetoFachada fachada = new ProjetoFachada();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            try {
                Date dataInicio = obj.dataInicio != null && !"".equals(obj.dataInicio) ? sdf.parse(obj.dataInicio) : null;
                Date dataTermino = obj.dataTermino != null && !"".equals(obj.dataTermino) ? sdf.parse(obj.dataTermino) : null;
                Float valor = obj.valorFinanciado != null && !"".equals(obj.valorFinanciado) ? Float.parseFloat(obj.valorFinanciado) : 0;
                Projeto projeto = fachada.editarProjeto(Integer.parseInt(obj.identificador), obj.titulo, dataInicio, dataTermino, obj.agenciaFinanciadora, valor, obj.objetivo, obj.descricao);

                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
