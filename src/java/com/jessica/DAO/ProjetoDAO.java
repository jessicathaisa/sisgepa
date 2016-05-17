/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.Colaborador;
import com.jessica.Modelo.Professor;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Publicacao;
import com.jessica.Modelo.StatusProjeto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class ProjetoDAO extends DAO{
    
    /**
     * Adiciona projeto a memoria
     * @param titulo
     * @param dataInicio
     * @param dataTermino
     * @param agenciaFinanciadora
     * @param valorFinanciado
     * @param objetivo
     * @param descricao
     * @return 
     */
    public Projeto addProjeto(String titulo, Date dataInicio, Date dataTermino, String agenciaFinanciadora, float valorFinanciado, String objetivo, String descricao){
        Projeto projeto = new Projeto();
        
        projeto.setTitulo(titulo);
        projeto.setAgenciaFinanciadora(agenciaFinanciadora);
        projeto.setDataInicio(dataInicio);
        projeto.setDataTermino(dataTermino);
        projeto.setDescricao(descricao);
        projeto.setObjetivo(objetivo);
        projeto.setValorFinanciado(valorFinanciado);
        projeto.setStatus(StatusProjeto.EM_ELABORACAO);
        
        try {
            List<Projeto> projetos = memoria.getProjetos();
            int id = 1;
            if(!projetos.isEmpty())
                id = projetos.get(projetos.size() - 1).getIdentificador() + 1;
            projeto.setIdentificador(id);

            memoria.getProjetos().add(projeto);
        } catch (OutOfMemoryError oomex) {
            return null;
        }
        
        return projeto;
    }
    
    /**
     * Editar dados de um projeto dado seu identifdicador
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
    public Projeto editarProjeto(int id,String titulo, Date dataInicio, Date dataTermino, String agenciaFinanciadora, float valorFinanciado, String objetivo, String descricao){
        Projeto projeto = buscar(id);
        
        try {
            projeto.setTitulo(titulo);
            projeto.setAgenciaFinanciadora(agenciaFinanciadora);
            projeto.setDataInicio(dataInicio);
            projeto.setDataTermino(dataTermino);
            projeto.setDescricao(descricao);
            projeto.setObjetivo(objetivo);
            projeto.setValorFinanciado(valorFinanciado);
            
        } catch (Exception ex) {
            return null;
        }
        
        return projeto;
    }
    
    /**
     * Remove um projeto do sistema
     * @param id
     * @return 
     */
    public boolean remProjeto(int id){
        List<Projeto> projetos = memoria.getProjetos();
        Projeto obj = null;
        for (Projeto proj : projetos) {
            if (proj.getIdentificador() == id) {
                obj = proj;
                break;
            }
        }
        try{
            for(Colaborador colaborador : obj.getParticipantes()){
                colaborador.getProjetos().remove(obj);
            }
            memoria.getProjetos().remove(obj);
        }
        catch(Exception ex){
            return false;
        }
        return true;
    }
    
    /**
     * Adiciona Participante no projeto
     * @param idProjeto
     * @param idParticipante
     * @return 
     */
    public Projeto addParticipante(int idProjeto, int idParticipante){
        Projeto p = buscar(idProjeto);
        if(p == null)
            return null;
        ColaboradorDAO colaboradordao = new ColaboradorDAO();
        Colaborador participante = colaboradordao.buscar(idParticipante);
        
        if(participante == null)
            return p;
        
        boolean participanteEstaNoProjeto = false;
        for(Colaborador colaborador : p.getParticipantes()){
            if(colaborador.equals(participante)){
                participanteEstaNoProjeto = true;
                break;
            }
        }
        
        if(!participanteEstaNoProjeto){
            p.getParticipantes().add(participante);
            participante.getProjetos().add(p);
        }
        
        return p;
    }
    
    /**
     * Remover Participante do projeto
     * @param idProjeto
     * @param idParticipante
     * @return 
     */
    public Projeto remParticipante(int idProjeto, int idParticipante){
        Projeto p = buscar(idProjeto);
        if(p == null)
            return null;
        ColaboradorDAO colaboradordao = new ColaboradorDAO();
        Colaborador participante = colaboradordao.buscar(idParticipante);
        
        if(participante == null)
            return p;
        
        boolean participanteEstaNoProjeto = false;
        for(Colaborador colaborador : p.getParticipantes()){
            if(colaborador.equals(participante)){
                participanteEstaNoProjeto = true;
                break;
            }
        }
        
        if(participanteEstaNoProjeto){
            p.getParticipantes().remove(participante);
            participante.getProjetos().remove(p);
        }
        
        return p;
    }
    
    /**
     * Adiciona publicacao no projeto
     * @param idProjeto
     * @param idPublicacao
     * @return 
     */
    public Projeto addPublicacao(int idProjeto, int idPublicacao){
        Projeto p = buscar(idProjeto);
        if(p == null)
            return null;
        PublicacaoDAO pubdao = new PublicacaoDAO();
        Publicacao publicacao = pubdao.buscar(idPublicacao);
        
        if(publicacao == null)
            return p;
        
        boolean publicacaoEstaNoProjeto = false;
        for(Publicacao pub : p.getPublicacoes()){
            if(pub.equals(publicacao)){
                publicacaoEstaNoProjeto = true;
                break;
            }
        }
        
        if(!publicacaoEstaNoProjeto){
            p.getPublicacoes().add(publicacao);
        }
        
        return p;
    }
    
    /**
     * Remover publicação do projeto
     * @param idProjeto
     * @param idPublicacao
     * @return 
     */
    public Projeto remPublicacao(int idProjeto, int idPublicacao){
        Projeto p = buscar(idProjeto);
        if(p == null)
            return null;
        PublicacaoDAO pubdao = new PublicacaoDAO();
        Publicacao publicacao = pubdao.buscar(idPublicacao);
        
        if(publicacao == null)
            return p;
        
        boolean publicacaoEstaNoProjeto = false;
        for(Publicacao pub : p.getPublicacoes()){
            if(pub.equals(publicacao)){
                publicacaoEstaNoProjeto = true;
                break;
            }
        }
        
        if(publicacaoEstaNoProjeto){
            p.getPublicacoes().remove(publicacao);
        }
        
        return p;
    }
    
    /**
     * Muda o status de um projeto
     * @param id
     * @param status
     * @return 
     */
    public Projeto mudarStatus(int id, StatusProjeto status){
        Projeto projeto = buscar(id);
        
        try {
            projeto.setStatus(status);
        }
        catch(Exception ex){
            return null;
        }
        
        return projeto;
    }
    
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
    
    /**
     * Buscar numero total de projetos
     * @return 
     */
    public int buscarTotal(){
        List<Projeto> projetos = memoria.getProjetos();
        
        return projetos.size();
    }
    
    /**
     * Buscar numero total de projetos
     * @param status
     * @return 
     */
    public int buscarTotal(StatusProjeto status){
        List<Projeto> projetos = memoria.getProjetos();
        int quantidade = 0;
        for (Projeto proj : projetos) {
            if (proj.getStatus() == status) {
                quantidade++;
            }
        }
        
        return quantidade;
    }
    
    /**
     * Retorna o total de professores existentes no projeto.
     * @param idProjeto
     * @return 
     */
    public int buscarTotalProfessores(int idProjeto){
        Projeto projeto = buscar(idProjeto);
        int quantidade = 0;
        for (Colaborador col : projeto.getParticipantes()) {
            if (col instanceof Professor) {
                quantidade++;
            }
        }
        
        return quantidade;
    }
    
    /**
     * Lista os projetos do sistema
     * @return 
     */
    public List<Projeto> listar(){
        return memoria.getProjetos();
    }
}
