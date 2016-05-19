/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Excecao.PossuiOrientandosException;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Fachada.ProfessorFachada;
import com.jessica.Modelo.ProducaoAcademica;
import com.jessica.Modelo.Professor;
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
public class ProfessorServlet extends ControladorCentral {

    class Retorno {
        public String comando;
        public String identificador;
        public String nome;
        public String email;
        public String usuario;
        public String senha;
        public String tipoUsuario;
    }

    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Retorno obj = (Retorno) gson.fromJson(request.getReader(), Retorno.class);

        if (obj.comando != null && obj.comando.equals("listarProfessores")) {
            ProfessorFachada fachada = new ProfessorFachada();
            List<Professor> professores = fachada.listarProfessores();
            String json = gson.toJson(professores);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (obj.comando != null && obj.comando.equals("listarProjetosProfessor")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                ProfessorFachada fachada = new ProfessorFachada();
                List<Projeto> projetos = null;
                
                try{
                    projetos = fachada.buscarProjetosProfessor(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(projetos);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } else if (obj.comando != null && obj.comando.equals("listarProducaoProfessor")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                ProfessorFachada fachada = new ProfessorFachada();
                List<ProducaoAcademica> producoes = null;
                
                try{
                    producoes = fachada.buscarProducoesProfessor(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(producoes);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } else if (obj.comando != null && obj.comando.equals("buscarProfessor")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                ProfessorFachada fachada = new ProfessorFachada();
                Professor professor = null;
                
                try{
                    professor = fachada.buscarProfessor(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(professor);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }  else if (obj.comando != null && obj.comando.equals("cadastrarProfessor")) {
            ProfessorFachada fachada = new ProfessorFachada();
            Professor professor = null;

            try {
                professor = fachada.cadastrarProfessor(obj.nome, obj.email, obj.usuario, obj.senha, obj.tipoUsuario);
                if(professor == null)
                    throw new Exception();
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (UsuarioDuplicadoException ex) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("editarProfessor")) {
            ProfessorFachada fachada = new ProfessorFachada();
            Professor professor = null;

            try {
                professor = fachada.editarProfessor(Integer.parseInt(obj.identificador), obj.nome, obj.email, obj.usuario, obj.senha, obj.tipoUsuario);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (UsuarioDuplicadoException ex) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("excluirProfessor")) {
            ProfessorFachada fachada = new ProfessorFachada();
            try {
                fachada.apagarProfessor(Integer.parseInt(obj.identificador));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch(PossuiOrientandosException pex){
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
