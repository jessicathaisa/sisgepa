/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

/**
 *
 * @author Jessica
 */
public class ColaboradorDAO extends DAO {
    public int buscarTotal(){
        return memoria.getAlunos().size() + memoria.getProfessores().size() +
                memoria.getPesquisadores().size();
    }
}
