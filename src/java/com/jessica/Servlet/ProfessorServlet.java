/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Fachada.ProfessorFachada;
import com.jessica.Modelo.Professor;
import java.io.IOException;
import java.text.ParseException;
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
        } else if (obj.comando != null && obj.comando.equals("cadastrarProfessor")) {
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
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
