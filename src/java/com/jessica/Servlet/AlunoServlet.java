/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Fachada.AlunoFachada;
import com.jessica.Fachada.ProjetoFachada;
import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.ProducaoAcademica;
import com.jessica.Modelo.Projeto;
import java.io.IOException;
import java.text.ParseException;
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
public class AlunoServlet extends ControladorCentral {

    class Retorno {

        public String identificador;
        public String nome;
        public String email;
        public String tipo;
        public String orientador;
        public String dataIngresso;
        public String regime;
        public String usuario;
        public String senha;
        public String tipoUsuario;
        public String comando;
    }

    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Retorno obj = (Retorno) gson.fromJson(request.getReader(), Retorno.class);

        if (obj.comando != null && obj.comando.equals("listarAlunos")) {
            AlunoFachada fachada = new AlunoFachada();
            List<Aluno> alunos = fachada.listarAlunos();
            try{
                String json = gson.toJson(alunos);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                response.setStatus(HttpServletResponse.SC_OK);
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("listarProjetosAluno")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                AlunoFachada fachada = new AlunoFachada();
                List<Projeto> projetos = null;
                
                try{
                    projetos = fachada.buscarProjetosAluno(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(projetos);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } else if (obj.comando != null && obj.comando.equals("listarProducaoAluno")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                AlunoFachada fachada = new AlunoFachada();
                List<ProducaoAcademica> producoes = null;
                
                try{
                    producoes = fachada.buscarProducoesAluno(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(producoes);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } else if (obj.comando != null && obj.comando.equals("buscarAluno")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                AlunoFachada fachada = new AlunoFachada();
                Aluno aluno = null;
                
                try{
                    aluno = fachada.buscarAluno(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(aluno);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } else if (obj.comando != null && obj.comando.equals("cadastrarAluno")) {
            AlunoFachada fachada = new AlunoFachada();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Aluno aluno = null;

            try {
                Date dataIngresso = sdf.parse(obj.dataIngresso);
                aluno = fachada.cadastrarAluno(obj.nome, obj.email, obj.tipo, Integer.parseInt(obj.orientador), dataIngresso, obj.regime, obj.usuario, obj.senha, obj.tipoUsuario);

                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (ParseException pe) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (UsuarioDuplicadoException ex) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("editarAluno")) {
            AlunoFachada fachada = new AlunoFachada();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Aluno aluno = null;

            try {
                Date dataIngresso = sdf.parse(obj.dataIngresso);
                aluno = fachada.editarAluno(Integer.parseInt(obj.identificador), obj.nome, obj.email, obj.tipo, Integer.parseInt(obj.orientador), dataIngresso, obj.regime, obj.usuario, obj.senha, obj.tipoUsuario);

                response.setStatus(HttpServletResponse.SC_OK);
            } catch (ParseException pe) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (UsuarioDuplicadoException ex) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("excluirAluno")) {
            AlunoFachada fachada = new AlunoFachada();
            try {
                fachada.apagarAluno(Integer.parseInt(obj.identificador));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
