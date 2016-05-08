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
import com.jessica.Modelo.Aluno;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class AlunoServlet extends ControladorCentral {

    class Retorno {

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

        if (obj.comando != null && obj.comando.equals("cadastrarAluno")) {
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
            } catch (Exception e){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}

}
