/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Pesquisador Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.PublicacaoDAO;
import com.jessica.Modelo.Publicacao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class PublicacaoFachada extends Fachada {

    /**
     * Listar as publicacoes
     *
     * @return
     */
    public List<Publicacao> listarPublicacoes() {
        PublicacaoDAO dao = new PublicacaoDAO();
        List<Publicacao> auxiliar = new ArrayList<>();
        List<Publicacao> lista = dao.listar();

        for (Publicacao o : lista) {
            Publicacao aux = o.copiaSimples();
            auxiliar.add(aux);
        }

        return auxiliar;
    }
}
