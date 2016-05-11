/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Pesquisador Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Excecao.PossuiOrientandosException;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Fachada.PesquisadorFachada;
import com.jessica.Modelo.Pesquisador;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class PesquisadorServlet extends ControladorCentral {

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

        if (obj.comando != null && obj.comando.equals("listarPesquisadores")) {
            PesquisadorFachada fachada = new PesquisadorFachada();
            List<Pesquisador> pesquisadores = fachada.listarPesquisadores();
            String json = gson.toJson(pesquisadores);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        }  else if (obj.comando != null && obj.comando.equals("buscarPesquisador")) {
            if (obj.identificador != null && obj.identificador.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                PesquisadorFachada fachada = new PesquisadorFachada();
                Pesquisador pesquisador = null;
                
                try{
                    pesquisador = fachada.buscarPesquisador(Integer.parseInt(obj.identificador));
                    String json = gson.toJson(pesquisador);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                }catch(NumberFormatException pe){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }  else if (obj.comando != null && obj.comando.equals("cadastrarPesquisador")) {
            PesquisadorFachada fachada = new PesquisadorFachada();
            Pesquisador pesquisador = null;

            try {
                pesquisador = fachada.cadastrarPesquisador(obj.nome, obj.email, obj.usuario, obj.senha, obj.tipoUsuario);
                if(pesquisador == null)
                    throw new Exception();
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (UsuarioDuplicadoException ex) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("editarPesquisador")) {
            PesquisadorFachada fachada = new PesquisadorFachada();
            Pesquisador pesquisador = null;

            try {
                pesquisador = fachada.editarPesquisador(Integer.parseInt(obj.identificador), obj.nome, obj.email, obj.usuario, obj.senha, obj.tipoUsuario);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (UsuarioDuplicadoException ex) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (obj.comando != null && obj.comando.equals("excluirPesquisador")) {
            PesquisadorFachada fachada = new PesquisadorFachada();
            try {
                fachada.apagarPesquisador(Integer.parseInt(obj.identificador));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
