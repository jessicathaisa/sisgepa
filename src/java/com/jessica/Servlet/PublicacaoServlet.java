/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Servlet;

import com.google.gson.Gson;
import com.jessica.Fachada.PublicacaoFachada;
import com.jessica.Modelo.Publicacao;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class PublicacaoServlet extends ControladorCentral {

    class Retorno {

        public String comando;
        public String identificador;
        public String titulo;
        public String ano;
        public String conferencia;
        public String autores;
        public String projeto;
    }

    @Override
    void processa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Retorno obj = (Retorno) gson.fromJson(request.getReader(), Retorno.class);

        if (obj.comando != null && obj.comando.equals("listarPublicacoes")) {
            PublicacaoFachada fachada = new PublicacaoFachada();
            List<Publicacao> publicacoes = fachada.listarPublicacoes();
            String json = gson.toJson(publicacoes);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (obj.comando != null && obj.comando.equals("cadastrarPublicacao")) {
            PublicacaoFachada fachada = new PublicacaoFachada();
            try {
                Publicacao publicacao = fachada.cadastrarPublicacao(obj.titulo, obj.conferencia, Integer.parseInt(obj.ano));
                
                String[] array = obj.autores.split(" ");
                for(String str : array){
                    int idAutor = Integer.parseInt(str);
                    Publicacao aux = fachada.adicionarAutor(publicacao.getIdentificador(), idAutor);
                    if(aux == null){
                        fachada.apagarPublicacao(publicacao.getIdentificador());
                        throw new Exception();
                    }
                }
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
