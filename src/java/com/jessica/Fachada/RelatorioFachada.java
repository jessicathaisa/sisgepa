/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.ColaboradorDAO;
import com.jessica.DAO.OrientacaoDAO;
import com.jessica.DAO.ProjetoDAO;
import com.jessica.DAO.PublicacaoDAO;
import com.jessica.Modelo.StatusProjeto;

/**
 *
 * @author Jessica
 */
public class RelatorioFachada extends Fachada {
    public Relatorio buscaRelatorio(){
        Relatorio relatorio = new Relatorio();
        
        ColaboradorDAO daocol = new ColaboradorDAO();
        ProjetoDAO daoproj = new ProjetoDAO();
        OrientacaoDAO daoori = new OrientacaoDAO();
        PublicacaoDAO daopub = new PublicacaoDAO();
        
        relatorio.setTotalColaboradores(daocol.buscarTotal());
        relatorio.setTotalOrientacoes(daoori.buscarTotal());
        relatorio.setTotalPublicacoes(daopub.buscarTotal());
        relatorio.setTotalProjetos(daoproj.buscarTotal());
        relatorio.setTotalProjetosEmElaboracao(daoproj.buscarTotal(StatusProjeto.EM_ELABORACAO));
        relatorio.setTotalProjetosEmAndamento(daoproj.buscarTotal(StatusProjeto.EM_ANDAMENTO));
        relatorio.setTotalProjetosConcluidos(daoproj.buscarTotal(StatusProjeto.CONCLUIDO));
        
        return relatorio;
    }
}
