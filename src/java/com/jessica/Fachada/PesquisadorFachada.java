/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Pesquisador Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.PesquisadorDAO;
import com.jessica.DAO.ProjetoDAO;
import com.jessica.DAO.PublicacaoDAO;
import com.jessica.DAO.UsuarioDAO;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Modelo.ProducaoAcademica;
import com.jessica.Modelo.Pesquisador;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Publicacao;
import com.jessica.Modelo.TipoUsuario;
import com.jessica.Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class PesquisadorFachada extends Fachada{
    /**
     * Lista os pesquisadores existentes no sistema
     * @return 
     */
    public List<Pesquisador> listarPesquisadores(){
        PesquisadorDAO dao = new PesquisadorDAO();

        List<Pesquisador> lista = dao.listar();
        List<Pesquisador> listaAuxiliar = new ArrayList<>();

        if (lista != null) {
            for (Pesquisador pesq : lista) {
                Pesquisador a = pesq.copiar();
                if (a.getProjetos() != null) {
                    for (Projeto p : a.getProjetos()) {
                        p.setParticipantes(null);
                    }
                }
                if (a.getProducoes() != null) {
                    for (ProducaoAcademica p : a.getProducoes()) {
                        if (p instanceof Publicacao) {
                            ((Publicacao) p).setAutores(null);
                        }
                    }
                }
                listaAuxiliar.add(a);
            }
        }

        return listaAuxiliar;
    }
    
    /**
     * Cadastrar o pesquisador no sistema
     * @param nome
     * @param email
     * @param loginUsuario
     * @param senhaUsuario
     * @param tipoUsuario
     * @return
     * @throws UsuarioDuplicadoException 
     */
    public Pesquisador cadastrarPesquisador(String nome, String email, String loginUsuario, String senhaUsuario, String tipoUsuario) throws UsuarioDuplicadoException {
        Pesquisador pesquisador = null;
        PesquisadorDAO dao = new PesquisadorDAO();
        UsuarioDAO usdao = new UsuarioDAO();
        Usuario usuario = null;

        // Verifica se o usuário já existe no sistema
        usuario = usdao.buscar(loginUsuario);
        if (usuario != null) {
            throw new UsuarioDuplicadoException();
        }

        // Tenta parsear os dados vindos do cliente
        try {
            TipoUsuario tipoUser = TipoUsuario.valueOf(tipoUsuario);
            usuario = usdao.addUsuario(loginUsuario, senhaUsuario, tipoUser);
            
            pesquisador = dao.addPesquisador(nome, email, usuario.getIdentificador());

        } catch (IllegalArgumentException ilg) {
            if (usuario != null) {
                usdao.remUsuario(usuario.getIdentificador());
            }
            return null;
        }

        return pesquisador;
    }

    /**
     * Editar dados do pesquisador
     * @param id
     * @param nome
     * @param email
     * @param loginUsuario
     * @param senhaUsuario
     * @param tipoUsuario
     * @return
     * @throws UsuarioDuplicadoException 
     */
    public Pesquisador editarPesquisador(int id, String nome, String email, String loginUsuario, String senhaUsuario, String tipoUsuario) throws UsuarioDuplicadoException {
        Pesquisador pesquisador = null;
        PesquisadorDAO dao = new PesquisadorDAO();
        UsuarioDAO usdao = new UsuarioDAO();
        Usuario usuario = null;
        pesquisador = dao.buscar(id);
        Usuario usuariopesquisador = pesquisador.getUsuario();

        if (loginUsuario.equals(usuariopesquisador.getLogin())) {
            // Não quero mudar.
            usuario = usuariopesquisador;
        } else {
            // Verifica se o usuário já existe no sistema
            usuario = usdao.buscar(loginUsuario);
            if (usuario != null) {
                throw new UsuarioDuplicadoException();
            } else {
                usdao.remUsuario(usuariopesquisador.getIdentificador());
                TipoUsuario tipoUser = TipoUsuario.valueOf(tipoUsuario);
                usuario = usdao.addUsuario(loginUsuario, senhaUsuario, tipoUser);
            }
        }
        
        // Tenta parsear os dados vindos do cliente
        try {
            pesquisador = dao.editarPesquisador(id, nome, email);
            pesquisador.setUsuario(usuario);

        } catch (IllegalArgumentException ilg) {
            if (usuario != null) {
                usdao.remUsuario(usuario.getIdentificador());
            }
            return null;
        }

        return pesquisador;
    }
    
    /**
     * Apagar os dados do pesquisador
     * @param id
     * @return 
     */
    public boolean apagarPesquisador(int id){
        PesquisadorDAO dao = new PesquisadorDAO();
        ProjetoDAO projdao = new ProjetoDAO();
        PublicacaoDAO pubdao = new PublicacaoDAO();
        try{
            Pesquisador pesquisador = dao.buscar(id);
            
            /*Remove dos projetos em que participa*/
            for(Projeto projeto : pesquisador.getProjetos()){
                projdao.remParticipante(projeto.getIdentificador(), pesquisador.getIdentificador());
            }
            
            for(ProducaoAcademica producao : pesquisador.getProducoes()){
                if(producao instanceof Publicacao)
                    pubdao.remAutor(producao.getIdentificador(), pesquisador.getIdentificador());
            }
            
            dao.remPesquisador(id);
        } catch(Exception ex){
            return false;
        }
        return true;
    }

    /**
     * Buscar um pesquisador pelo id
     * @param id
     * @return 
     */
    public Pesquisador buscarPesquisador(int id) {
        PesquisadorDAO dao = new PesquisadorDAO();
        Pesquisador a = dao.buscar(id).copiar();
        if (a.getProjetos() != null) {
            for (Projeto p : a.getProjetos()) {
                p.setParticipantes(null);
            }
        }
        if (a.getProducoes() != null) {
            for (ProducaoAcademica p : a.getProducoes()) {
                if (p instanceof Publicacao) {
                    ((Publicacao) p).setAutores(null);
                }
            }
        }
        return a;
    }

}
