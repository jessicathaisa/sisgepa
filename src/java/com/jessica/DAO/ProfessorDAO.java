/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.Professor;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Usuario;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class ProfessorDAO extends ColaboradorDAO {
    
    /**
     * Adiciona Professor a memória
     * @param nome
     * @param email
     * @param idUsuario
     * @return 
     */
    public Professor addProfessor(String nome, String email, String idUsuario) {
        UsuarioDAO userDao = new UsuarioDAO();
        Usuario usuario = userDao.buscar(idUsuario);
        if(usuario == null)
            return null;
        
        Professor prof = new Professor();
        prof.setEmail(email);
        prof.setNome(nome);
        prof.setUsuario(usuario);

        try {
            List<Professor> professores = memoria.getProfessores();
            int id = 1;
            if(!professores.isEmpty())
                id = professores.get(professores.size() - 1).getIdentificador();
            prof.setIdentificador(id);

            memoria.getProfessores().add(prof);
        } catch (OutOfMemoryError oomex) {
            return null;
        }

        return prof;
    }

    /**
     * Editar dados do Professor
     * @param identificador
     * @param nome
     * @param email
     * @return 
     */
    public Professor editarProfessor(int identificador, String nome, String email) {
        Professor prof = null;
        try {
            prof = buscar(identificador);
            prof.setEmail(email);
            prof.setNome(nome);
        } catch (Exception ex) {
            return null;
        }

        return prof;
    }

    /**
     * Remover Professor pelo identificador
     * @param id
     * @return 
     */
    public boolean remProfessor(int id) {
        List<Professor> professores = memoria.getProfessores();
        Professor obj = null;
        for (Professor prof : professores) {
            if (prof.getIdentificador() == id) {
                obj = prof;
                break;
            }
        }
        try {
            memoria.getProfessores().remove(obj);
        } catch (Exception ex) {
            return false;
        }        
        return true;
    }

    /**
     * Busca Professor por identificador
     * @param id
     * @return 
     */
    @Override
    public Professor buscar(int id) {
        List<Professor> professores = memoria.getProfessores();
        for (Professor pesq : professores) {
            if (pesq.getIdentificador() == id) {
                return pesq;
            }
        }
        return null;
    }

    /**
     * Listar Professor da memória
     * @return 
     */
    public List<Professor> listar(){
        List<Professor> professores = memoria.getProfessores();
        return professores;
    }
    
    /**
     * Busca total de projetos no qual o colaborador está alocado
     * @param id
     * @return 
     */
    public int buscarTotalProjetosAlocado(int id){
        Professor aluno = buscar(id);
        
        aluno.getProjetos().size();
        return 0;
    }
    
    /**
     * adiciona um participante ao projeto
     * @param professor
     * @param projeto 
     */
    public void adicionaProjeto(int professor, int projeto){
        Professor prof = buscar(professor);
        ProjetoDAO projetoDao = new ProjetoDAO();
        Projeto projetoobj = projetoDao.buscar(projeto);
        if(projetoobj != null && prof!=null){
            prof.getProjetos().add(projetoobj);
            projetoobj.getParticipantes().add(prof);
        }
    }
    
    /**
     * remove um professor do projeto
     * @param professor
     * @param projeto 
     */
    public void removerProjeto(int professor, int projeto){
        Professor prof = buscar(professor);
        ProjetoDAO projetoDao = new ProjetoDAO();
        Projeto projetoobj = projetoDao.buscar(projeto);
        if(projetoobj != null && prof!=null){
            prof.getProjetos().remove(projetoobj);
            projetoobj.getParticipantes().remove(prof);
        }
    }
}
