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
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class ProjetoFachada extends Fachada {
    
    /**
     * Listar Projetos em Andamento
     * @return 
     */
    public List<Projeto> listarEmAndamento(){
        ProjetoDAO dao = new ProjetoDAO();
        List<Projeto> projetos = dao.listar();
        List<Projeto> projetosAux = new ArrayList();
        
        for(Projeto projeto : projetos)
            if(projeto.getStatus() == StatusProjeto.EM_ANDAMENTO)
                projetosAux.add(projeto.copiar());
        
        return projetosAux;
    }
    
    /**
     * Buscar projeto por identificador
     * @param id
     * @return 
     */
    public Projeto buscar(int id){
        ProjetoDAO dao = new ProjetoDAO();
        
        return dao.buscar(id).copiaSimples();
    }
    
    /**
     * Listar Projetos
     * @return 
     */
    public List<Projeto> listar(){
        ProjetoDAO dao = new ProjetoDAO();
        List<Projeto> projetos = dao.listar();
        List<Projeto> projetosAux = new ArrayList();
        
        for(Projeto projeto : projetos)
                projetosAux.add(projeto.copiar());
        
        return projetosAux;
    }
    
    /**
     * Cadastrar um novo projeto
     * @param titulo
     * @param dataInicio
     * @param dataTermino
     * @param agenciaFinanciadora
     * @param valorFinanciado
     * @param objetivo
     * @param descricao
     * @return 
     */
    public Projeto cadastrarProjeto(String titulo, Date dataInicio, Date dataTermino, String agenciaFinanciadora, float valorFinanciado, String objetivo, String descricao){
        ProjetoDAO dao = new ProjetoDAO();
        
        Projeto projeto = dao.addProjeto(titulo, dataInicio, dataTermino, agenciaFinanciadora, valorFinanciado, objetivo, descricao);
        
        return projeto;
    }
    
    /**
     * Editar dados básicos do projeto
     * @param id
     * @param titulo
     * @param dataInicio
     * @param dataTermino
     * @param agenciaFinanciadora
     * @param valorFinanciado
     * @param objetivo
     * @param descricao
     * @return 
     */
    public Projeto editarProjeto(int id, String titulo, Date dataInicio, Date dataTermino, String agenciaFinanciadora, float valorFinanciado, String objetivo, String descricao){
        ProjetoDAO dao = new ProjetoDAO();
        
        Projeto projeto = dao.editarProjeto(id, titulo, dataInicio, dataTermino, agenciaFinanciadora, valorFinanciado, objetivo, descricao);
        
        return projeto;
    }
    
    /**
     * Dá andamento a um projeto
     * @param id
     * @return 
     */
    public Projeto darAndamento(int id){
        ProjetoDAO dao = new ProjetoDAO();
        Projeto projeto = dao.mudarStatus(id, StatusProjeto.EM_ANDAMENTO);
        return projeto;
    }
    
    /**
     * Conclui a um projeto
     * @param id
     * @return 
     */
    public Projeto darConcluir(int id){
        ProjetoDAO dao = new ProjetoDAO();
        Projeto projeto = dao.mudarStatus(id, StatusProjeto.CONCLUIDO);
        return projeto;
    }
}
