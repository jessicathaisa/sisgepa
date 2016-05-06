/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.Pesquisador;
import com.jessica.Modelo.ProducaoAcademica;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class PesquisadorDAO extends DAO {
    
    /**
     * Adiciona Pesquisador a memória
     * @param nome
     * @param email
     * @param idUsuario
     * @return 
     */
    public Pesquisador addPesquisador(String nome, String email, String idUsuario) {
        UsuarioDAO userDao = new UsuarioDAO();
        Usuario usuario = userDao.buscar(idUsuario);
        if(usuario == null)
            return null;
        
        Pesquisador pesq = new Pesquisador();
        pesq.setEmail(email);
        pesq.setNome(nome);
        pesq.setUsuario(usuario);

        try {
            List<Pesquisador> pesquisadores = memoria.getPesquisadores();
            int id = 1;
            if(pesquisadores.size() != 0)
                id = pesquisadores.get(pesquisadores.size() - 1).getIdentificador();
            pesq.setIdentificador(id);

            memoria.getPesquisadores().add(pesq);
        } catch (OutOfMemoryError oomex) {
            return null;
        }

        return pesq;
    }

    /**
     * Editar dados do pesquisador
     * @param identificador
     * @param nome
     * @param email
     * @return 
     */
    public Pesquisador editarPesquisador(int identificador, String nome, String email) {
        Pesquisador pesq = null;
        try {
            pesq = buscar(identificador);
            pesq.setEmail(email);
            pesq.setNome(nome);
        } catch (Exception ex) {
            return null;
        }

        return pesq;
    }

    /**
     * Remover Pesquisador pelo identificador
     * @param id
     * @return 
     */
    public boolean remPesquisador(int id) {
        List<Pesquisador> pesquisadores = memoria.getPesquisadores();

        for (Pesquisador pesq : pesquisadores) {
            if (pesq.getIdentificador() == id) {
                try {
                    memoria.getPesquisadores().remove(pesq);
                } catch (Exception ex) {
                    return false;
                }
                return true;
            }
        }

        return true;
    }

    /**
     * Busca Pesquisador por identificador
     * @param id
     * @return 
     */
    public Pesquisador buscar(int id) {
        List<Pesquisador> pesquisadores = memoria.getPesquisadores();
        for (Pesquisador pesq : pesquisadores) {
            if (pesq.getIdentificador() == id) {
                return pesq;
            }
        }
        return null;
    }

    /**
     * Listar pesquisadores da memória
     * @return 
     */
    public List<Pesquisador> listar(){
        List<Pesquisador> pesquisadores = memoria.getPesquisadores();
        return pesquisadores;
    }
    
    /**
     * Busca total de projetos no qual o colaborador está alocado
     * @param id
     * @return 
     */
    public int buscarTotalProjetosAlocado(int id){
        Pesquisador aluno = buscar(id);
        
        aluno.getProjetos().size();
        return 0;
    }
    
    /**
     * adiciona um participante ao projeto
     * @param pesquisador
     * @param projeto 
     */
    public void adicionaProjeto(int pesquisador, int projeto){
        Pesquisador pesq = buscar(pesquisador);
        ProjetoDAO projetoDao = new ProjetoDAO();
        Projeto projetoobj = projetoDao.buscar(projeto);
        if(projetoobj != null && pesq!=null){
            pesq.getProjetos().add(projetoobj);
            projetoobj.getParticipantes().add(pesq);
        }
    }
    
    /**
     * remove um pesquisador do projeto
     * @param pesquisador
     * @param projeto 
     */
    public void removerProjeto(int pesquisador, int projeto){
        Pesquisador pesq = buscar(pesquisador);
        ProjetoDAO projetoDao = new ProjetoDAO();
        Projeto projetoobj = projetoDao.buscar(projeto);
        if(projetoobj != null && pesq!=null){
            pesq.getProjetos().remove(projetoobj);
            projetoobj.getParticipantes().remove(pesq);
        }
    }
}
