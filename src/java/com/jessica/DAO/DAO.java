/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Memoria.Memoria;

/**
 *
 * @author Jessica
 */
public class DAO {
    protected Memoria memoria;

    public DAO() {
        memoria = Memoria.getInstance();
    }
    
}
