/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.ProfessorDAO;
import com.jessica.Modelo.Professor;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class ProfessorFachada extends Fachada{
    public List<Professor> listarProfessores(){
        ProfessorDAO dao = new ProfessorDAO();
        return dao.listar();
    }
}
