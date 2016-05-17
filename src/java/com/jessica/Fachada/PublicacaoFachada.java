/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Pesquisador Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.PublicacaoDAO;
import com.jessica.Modelo.Colaborador;
import com.jessica.Modelo.Publicacao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class PublicacaoFachada extends Fachada {

    /**
     * Cadastra um publicação no sistema
     *
     * @param titulo
     * @param conferencia
     * @param ano
     * @return
     */
    public Publicacao cadastrarPublicacao(String titulo, String conferencia, int ano) {
        PublicacaoDAO dao = new PublicacaoDAO();

        return dao.addPublicacao(titulo, conferencia, ano);
    }
    
    /**
     * Editar os dados da publicação
     * @param id
     * @param titulo
     * @param conferencia
     * @param ano
     * @return 
     */
    public Publicacao editarPublicacao(int id, String titulo, String conferencia, int ano) {
        PublicacaoDAO dao = new PublicacaoDAO();
        
        Publicacao pub = dao.editarPublicacao(id, titulo, conferencia, ano);
        List<Colaborador> auxiliar = new ArrayList<>();
        
        for(Colaborador colaborador : pub.getAutores()){
            auxiliar.add(colaborador);
        }
        for(Colaborador colaborador : auxiliar){
            dao.remAutor(id, colaborador.getIdentificador());
        }
        
        return pub;
    }
    
    /**
     * Adiciona autor a publicacao
     *
     * @param idPublicacao
     * @param idAutor
     * @return
     */
    public Publicacao adicionarAutor(int idPublicacao, int idAutor) {
        PublicacaoDAO dao = new PublicacaoDAO();

        Publicacao p = dao.addAutor(idPublicacao, idAutor);

        return p;
    }
    
    /**
     * Atribui um projeto a uma publicação
     * @param idPublicacao
     * @param idProjeto
     * @return 
     */
    public Publicacao atribuirProjeto(int idPublicacao, int idProjeto){
        PublicacaoDAO dao = new PublicacaoDAO();

        Publicacao p = dao.setProjeto(idPublicacao, idProjeto);

        return p;        
    }

    /**
     * Apaga uma publicação do sistema
     *
     * @param id
     * @return
     */
    public boolean apagarPublicacao(int id) {
        PublicacaoDAO dao = new PublicacaoDAO();
        Publicacao pub = buscarPublicacao(id);
        for (Colaborador colaborador : pub.getAutores()) {
            colaborador.getProducoes().remove(pub);
        }

        return dao.remPublicacao(id);
    }

    /**
     * Busca publicação pelo id
     *
     * @param id
     * @return
     */
    public Publicacao buscarPublicacao(int id) {
        PublicacaoDAO dao = new PublicacaoDAO();

        return dao.buscar(id).copiaSimples();
    }

    /**
     * Listar as publicacoes
     *
     * @return
     */
    public List<Publicacao> listarPublicacoes() {
        PublicacaoDAO dao = new PublicacaoDAO();
        List<Publicacao> auxiliar = new ArrayList<>();
        List<Publicacao> lista = dao.listar();

        for (Publicacao o : lista) {
            Publicacao aux = o.copiaSimples();
            auxiliar.add(aux);
        }

        return auxiliar;
    }
    
    
}
