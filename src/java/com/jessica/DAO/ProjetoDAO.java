/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.Projeto;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class ProjetoDAO extends DAO{
    
    
    
    /**
     * Busca projeto pelo identificador
     * @param id
     * @return 
     */
    public Projeto buscar(int id) {
        List<Projeto> projetos = memoria.getProjetos();
        for (Projeto proj : projetos) {
            if (proj.getIdentificador() == id) {
                return proj;
            }
        }
        return null;
    }
    
}
