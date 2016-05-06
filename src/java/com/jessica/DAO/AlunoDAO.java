/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.Professor;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.RegimeCurso;
import com.jessica.Modelo.TipoAluno;
import com.jessica.Modelo.TipoProjetoAluno;
import com.jessica.Modelo.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class AlunoDAO extends ColaboradorDAO{
    
    /**
     * Adiciona Aluno na memória
     * @param nome
     * @param email
     * @param tipo
     * @param orientador
     * @param dataIngresso
     * @param regime
     * @param idUsuario
     * @return 
     */
    public Aluno addAluno(String nome, String email, TipoAluno tipo, int orientador, Date dataIngresso, RegimeCurso regime, int idUsuario){
        UsuarioDAO userDao = new UsuarioDAO();
        Usuario usuario = userDao.buscar(idUsuario);
        if(usuario == null)
            return null;
        ProfessorDAO profDao = new ProfessorDAO();
        Professor professor = profDao.buscar(orientador);
        
        Aluno aluno = new Aluno();
        aluno.setEmail(email);
        aluno.setNome(nome);
        aluno.setUsuario(usuario);
        aluno.setTipoAluno(tipo);
        aluno.setOrientador(professor);
        aluno.setDataIngresso(dataIngresso);
        aluno.setRegimeCurso(regime);
        
        if(tipo == TipoAluno.GRADUACAO)
            aluno.setTipoProjeto(TipoProjetoAluno.TRABALHO_CONCLUSAO_CURSO);
        else if(tipo == TipoAluno.MESTRADO)
            aluno.setTipoProjeto(TipoProjetoAluno.DISSERTACAO);
        else if(tipo == TipoAluno.DOUTORADO)
            aluno.setTipoProjeto(TipoProjetoAluno.TESE);
        
        if(tipo == TipoAluno.GRADUACAO)
            aluno.setRegimeCurso(RegimeCurso.NAO_SE_APLICA);

        try {
            List<Aluno> alunos = memoria.getAlunos();
            int id = 1;
            if(alunos.size() != 0)
                id = alunos.get(alunos.size() - 1).getIdentificador();
            aluno.setIdentificador(id);

            memoria.getAlunos().add(aluno);
        } catch (OutOfMemoryError oomex) {
            return null;
        }

        return aluno;
    }
    
        /**
     * Adiciona Aluno na memória
     * @param nome
     * @param email
     * @param tipo
     * @param orientador
     * @param dataIngresso
     * @param regime
     * @param idUsuario
     * @return 
     */
    public Aluno editarAluno(int id, String nome, String email, TipoAluno tipo, int orientador, Date dataIngresso, RegimeCurso regime){
        ProfessorDAO profDao = new ProfessorDAO();
        Professor professor = profDao.buscar(orientador);
        
        Aluno aluno = null;
        try {
            aluno = buscar(id);
            aluno.setEmail(email);
            aluno.setNome(nome);
            aluno.setTipoAluno(tipo);
            aluno.setOrientador(professor);
            aluno.setDataIngresso(dataIngresso);
            aluno.setRegimeCurso(regime);

            if(tipo == TipoAluno.GRADUACAO)
                aluno.setTipoProjeto(TipoProjetoAluno.TRABALHO_CONCLUSAO_CURSO);
            else if(tipo == TipoAluno.MESTRADO)
                aluno.setTipoProjeto(TipoProjetoAluno.DISSERTACAO);
            else if(tipo == TipoAluno.DOUTORADO)
                aluno.setTipoProjeto(TipoProjetoAluno.TESE);

            if(tipo == TipoAluno.GRADUACAO)
                aluno.setRegimeCurso(RegimeCurso.NAO_SE_APLICA);
        } catch (OutOfMemoryError oomex) {
            return null;
        }

        return aluno;
    }
    
    /**
     * Remover Aluno do sistema
     * @param id
     * @return 
     */
    public boolean remAluno(int id) {
        List<Aluno> alunos = memoria.getAlunos();
        Aluno obj = null;
        for (Aluno aluno : alunos) {
            if (aluno.getIdentificador() == id) {
                obj = aluno;
                break;
            }
        }
        try {
            memoria.getAlunos().remove(obj);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Busca Aluno por identificador
     * @param id
     * @return 
     */
    @Override
    public Aluno buscar(int id) {
        List<Aluno> alunos = memoria.getAlunos();
        for (Aluno aluno : alunos) {
            if (aluno.getIdentificador() == id) {
                return aluno;
            }
        }
        return null;
    }
    
    /**
     * Listar os Alunos existentes na memória
     * @return 
     */
    public List<Aluno> listar(){
        List<Aluno> alunos = memoria.getAlunos();
        return alunos;
    }
    
    /**
     * Listar os Alunos existentes na memória por tipo de aluno
     * @param tipo
     * @return 
     */
    public List<Aluno> listar(TipoAluno tipo){
        List<Aluno> alunos = new ArrayList<>(memoria.getAlunos());
        for (Aluno aluno : memoria.getAlunos()) {
            if (aluno.getTipoAluno() != tipo) {
                alunos.remove(aluno);
            }
        }
        return alunos;
    }
    
    /**
     * Busca total de projetos no qual o colaborador está alocado
     * @param id
     * @return 
     */
    public int buscarTotalProjetosAlocado(int id){
        Aluno aluno = buscar(id);
        
        aluno.getProjetos().size();
        return 0;
    }
    
    /**
     * adiciona um participante ao projeto
     * @param aluno
     * @param projeto 
     */
    public void adicionaProjeto(int aluno, int projeto){
        Aluno alunoobj = buscar(aluno);
        ProjetoDAO projetoDao = new ProjetoDAO();
        Projeto projetoobj = projetoDao.buscar(projeto);
        if(projetoobj != null && alunoobj!=null){
            alunoobj.getProjetos().add(projetoobj);
            projetoobj.getParticipantes().add(alunoobj);
        }
    }
    
    /**
     * remove um aluno do projeto
     * @param aluno
     * @param projeto 
     */
    public void removerProjeto(int aluno, int projeto){
        Aluno alunoobj = buscar(aluno);
        ProjetoDAO projetoDao = new ProjetoDAO();
        Projeto projetoobj = projetoDao.buscar(projeto);
        if(projetoobj != null && alunoobj!=null){
            alunoobj.getProjetos().remove(projetoobj);
            projetoobj.getParticipantes().remove(alunoobj);
        }
    }
}
