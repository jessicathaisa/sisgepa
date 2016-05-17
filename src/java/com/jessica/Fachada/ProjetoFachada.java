/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.ProjetoDAO;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.StatusProjeto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class ProjetoFachada extends Fachada {
    
    public List<Projeto> listarEmAndamento(){
        ProjetoDAO dao = new ProjetoDAO();
        List<Projeto> projetos = dao.listar();
        List<Projeto> projetosAux = new ArrayList();
        
        for(Projeto projeto : projetos)
            if(projeto.getStatus() == StatusProjeto.EM_ANDAMENTO)
                projetosAux.add(projeto.copiar());
        
        return projetosAux;
        
    }
}
