/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.Orientacao;
import com.jessica.Modelo.Professor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class OrientacaoDAO extends DAO{
    
    /**
     * Adicionar Orientacao a Memoria
     * @param idAluno
     * @param idProfessor
     * @param titulo
     * @param ano
     * @return 
     */
    public Orientacao addOrientacao(int idAluno, int idProfessor, String titulo, int ano){
        Orientacao orientacao = null;
        AlunoDAO alunoDao = new AlunoDAO();
        Aluno aluno = alunoDao.buscar(idAluno);
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor professor = professorDao.buscar(idProfessor);
        
        if(aluno != null && professor != null){
            orientacao = new Orientacao();
            orientacao.setAluno(aluno);
            orientacao.setProfessor(professor);
            orientacao.setTitulo(titulo);
            orientacao.setAno(ano);
            
            try {
                List<Orientacao> orientacoes = memoria.getOrientacoes();
                int id = 1;
                if(!orientacoes.isEmpty())
                    id = orientacoes.get(orientacoes.size() - 1).getIdentificador() + 1;
                orientacao.setIdentificador(id);

                memoria.getOrientacoes().add(orientacao);
            } catch (OutOfMemoryError oomex) {
                return null;
            }
            
            aluno.getProducoes().add(orientacao);
            professor.getProducoes().add(orientacao);
        }
        
        return orientacao;
    }
    
    /**
     * Remover Orientacao da Memória
     * @param idAluno
     * @param idProfessor
     * @return 
     */
    public boolean remOrientacao(int idAluno, int idProfessor){
        List<Orientacao> orientacoes = memoria.getOrientacoes();
        Orientacao obj = null;
        for(Orientacao o : orientacoes){
            if(o.getAluno().getIdentificador() == idAluno && o.getProfessor().getIdentificador() == idProfessor){
                obj = o;
                break;
            }
        }
        if(obj == null)
            return false;
        try {
            obj.getAluno().getProducoes().remove(obj);
            obj.getProfessor().getProducoes().remove(obj);
            memoria.getOrientacoes().remove(obj);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Listar as orientações da memória
     * @return 
     */
    public List<Orientacao> listar(){
        List<Orientacao> orientacoes = memoria.getOrientacoes();
        return orientacoes;
    }
    
    /**
     * Encontra uma orientacao pelo id do aluno e o id do professor
     * @param idAluno
     * @param idProfessor
     * @return 
     */
    public Orientacao buscar(int idAluno, int idProfessor){
        List<Orientacao> orientacoes = memoria.getOrientacoes();
        Orientacao obj = null;
        for(Orientacao o : orientacoes){
            if(o.getAluno().getIdentificador() == idAluno && o.getProfessor().getIdentificador() == idProfessor){
                obj = o;
                break;
            }
        }
        
        return obj;
    }
    
    /**
     * Encontra uma orientacao pelo id da orientação
     * @param id
     * @return 
     */
    public Orientacao buscar(int id){
        List<Orientacao> orientacoes = memoria.getOrientacoes();
        Orientacao obj = null;
        for(Orientacao o : orientacoes){
            if(o.getIdentificador() == id){
                obj = o;
                break;
            }
        }
        
        return obj;
    }
    
    /**
     * traz a lista de orientacoes dado um aluno
     * @param idAluno
     * @return 
     */
    public List<Orientacao> buscarPorAluno(int idAluno){
        List<Orientacao> orientacoes = memoria.getOrientacoes();
        List<Orientacao> obj = new ArrayList<>();
        for(Orientacao o : orientacoes){
            if(o.getAluno().getIdentificador() == idAluno){
                obj.add(o);
            }
        }
        return obj;
    }
    
    /**
     * traz a lista de orientações dado um professor
     * @param idProfessor
     * @return 
     */
    public List<Orientacao> buscarPorProfessor(int idProfessor){
        List<Orientacao> orientacoes = memoria.getOrientacoes();
        List<Orientacao> obj = new ArrayList<>();
        for(Orientacao o : orientacoes){
            if(o.getProfessor().getIdentificador() == idProfessor){
                obj.add(o);
            }
        }
        return obj;
    }
    
    /**
     * Buscar numero total de orientações
     * @return 
     */
    public int buscarTotal(){
        List<Orientacao> orientacoes = memoria.getOrientacoes();
        return orientacoes.size();
    }
}
