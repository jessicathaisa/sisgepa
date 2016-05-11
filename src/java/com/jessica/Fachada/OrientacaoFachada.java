/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Pesquisador Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.OrientacaoDAO;
import com.jessica.Modelo.Orientacao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class OrientacaoFachada extends Fachada{

    /**
     * Listar as orientações
     * @return 
     */
    public List<Orientacao> listarOrientacoes(){
        OrientacaoDAO dao = new OrientacaoDAO();
        List<Orientacao> auxiliar = new ArrayList<>();
        List<Orientacao> lista = dao.listar();
        
        for(Orientacao o : lista){
            o.getAluno().setProducoes(null);
            o.getProfessor().setProducoes(null);
            o.getAluno().setProjetos(null);
            o.getProfessor().setProjetos(null);
            auxiliar.add(o);
        }
        
        return auxiliar;
    }

    /**
     * Buscar uma orientação pelo id
     * @param id
     * @return 
     */
    public Orientacao buscarOrientacao(int id) {
        OrientacaoDAO dao = new OrientacaoDAO();
        Orientacao auxiliar = dao.buscar(id);
        Orientacao o = new Orientacao();
        
        o.setAluno(auxiliar.getAluno());
        o.setProfessor(auxiliar.getProfessor());
        o.setIdentificador(auxiliar.getIdentificador());
        o.setTitulo(auxiliar.getTitulo());
        
        o.getAluno().setProducoes(null);
        o.getProfessor().setProducoes(null);
        o.getAluno().setProjetos(null);
        o.getProfessor().setProjetos(null);
            
        return o;
    }

}
