/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.ProjetoDAO;
import com.jessica.Modelo.Colaborador;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Publicacao;
import com.jessica.Modelo.StatusProjeto;
import com.jessica.TratarReferenciaCircular;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class ProjetoFachada{
    
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
                projetosAux.add(TratarReferenciaCircular.tratar(projeto));
        
        return projetosAux;
    }
    
    /**
     * Buscar projeto por identificador
     * @param id
     * @return 
     */
    public Projeto buscar(int id){
        ProjetoDAO dao = new ProjetoDAO();
        
        return TratarReferenciaCircular.tratar(dao.buscar(id));
    }
    
    /**
     * Listar Projetos
     * @return 
     */
    public List<Projeto> listar(){
        ProjetoDAO dao = new ProjetoDAO();
        List<Projeto> projetos = dao.listar();
        
        return TratarReferenciaCircular.tratarLista(projetos);
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
    
    /**
     * Adiciona um participante ao projeto
     * @param idProjeto
     * @param idParticipantes
     * @return 
     */
    public Projeto adicionarParticipante(int idProjeto, int idParticipantes){
        ProjetoDAO dao = new ProjetoDAO();
        
        return dao.addParticipante(idProjeto, idParticipantes);
    }
    
    /**
     * Remove todos os participantes do projeto
     * @param idProjeto
     */
    public void removerTodosParticipantes(int idProjeto){
        ProjetoDAO dao = new ProjetoDAO();
        
        List<Colaborador> colaboradores = buscar(idProjeto).getParticipantes();
        
        for(Colaborador c : colaboradores)
            dao.remParticipante(idProjeto, c.getIdentificador());
    }
    
    /**
     * Adiciona uma publicação ao projeto
     * @param idProjeto
     * @param idPublicacao
     * @return 
     */
    public Projeto adicionarPublicacao(int idProjeto, int idPublicacao){
        ProjetoDAO dao = new ProjetoDAO();
        
        return dao.addPublicacao(idProjeto, idPublicacao);
    }
    
    /**
     * Remove todos as publicações do projeto
     * @param idProjeto
     */
    public void removerTodasPublicacoes(int idProjeto){
        ProjetoDAO dao = new ProjetoDAO();
        
        List<Publicacao> publicacoes = buscar(idProjeto).getPublicacoes();
        
        for(Publicacao c : publicacoes)
            dao.remPublicacao(idProjeto, c.getIdentificador());
    }
    /**
     * Apaga um projeto do sistema
     *
     * @param id
     * @return
     */
    public boolean apagarProjeto(int id) {
        ProjetoDAO dao = new ProjetoDAO();
        removerTodosParticipantes(id);
        removerTodasPublicacoes(id);

        return dao.remProjeto(id);
    }
}
