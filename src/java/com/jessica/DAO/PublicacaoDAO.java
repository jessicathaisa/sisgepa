/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.DAO;

import com.jessica.Modelo.Colaborador;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Publicacao;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class PublicacaoDAO extends DAO {

    /**
     * Adiciona publicação a memoria
     *
     * @param titulo
     * @param conferencia
     * @param ano
     * @return
     */
    public Publicacao addPublicacao(String titulo, String conferencia, int ano) {
        Publicacao pub = new Publicacao();

        pub.setAno(ano);
        pub.setTitulo(titulo);
        pub.setConferencia(conferencia);
        try {
            List<Publicacao> publicacoes = memoria.getPublicacoes();
            int id = 1;
            if (!publicacoes.isEmpty()) {
                id = publicacoes.get(publicacoes.size() - 1).getIdentificador();
            }
            pub.setIdentificador(id);

            memoria.getPublicacoes().add(pub);
        } catch (OutOfMemoryError oomex) {
            return null;
        }
        return pub;
    }

    /**
     * Edita os dados da publicação
     * @param id
     * @param titulo
     * @param conferencia
     * @param ano
     * @return 
     */
    public Publicacao editarPublicacao(int id, String titulo, String conferencia, int ano) {
        Publicacao pub = buscar(id);
        if (pub == null) {
            return pub;
        }

        try {
            pub.setAno(ano);
            pub.setTitulo(titulo);
            pub.setConferencia(conferencia);
        } catch (Exception ex) {
            return null;
        }
        return pub;
    }

    /**
     * Remover Publicação da Memória pelo identificador
     *
     * @param id
     * @return
     */
    public boolean remOrientacao(int id) {
        List<Publicacao> publicacoes = memoria.getPublicacoes();
        Publicacao obj = null;
        for (Publicacao o : publicacoes) {
            if (o.getIdentificador() == id) {
                obj = o;
                break;
            }
        }
        try {
            memoria.getPublicacoes().remove(obj);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Alterar o projeto da publicação. Alterando tbm a lista de publicações do projeto.
     * @param idPublicacao
     * @param idProjeto
     * @return 
     */
    public Publicacao setProjeto(int idPublicacao, int idProjeto){
        Publicacao pub = buscar(idPublicacao);
        if(pub == null)
            return null;
        ProjetoDAO projetodao = new ProjetoDAO();
        Projeto projeto = projetodao.buscar(idProjeto);
        
        if(pub.getProjeto() != null){
            /* Vamos trocar a publicação do projeto */
            pub.getProjeto().getPublicacoes().remove(pub);
        }
        
        pub.setProjeto(projeto);
        if(projeto != null)
            projeto.getPublicacoes().add(pub);
        
        return pub;
    }

    /**
     * Relaciona um colaborador a uma publicação
     * @param idPublicacao
     * @param idAutor
     * @return 
     */
    public Publicacao addAutor(int idPublicacao, int idAutor){
        Publicacao pub = buscar(idPublicacao);
        if(pub == null)
            return null;
        ColaboradorDAO colaboradordao = new ColaboradorDAO();
        Colaborador autor = colaboradordao.buscar(idAutor);
        
        if(autor == null)
            return pub;
        
        boolean autorEstaNaPublicacao = false;
        for(Colaborador colaborador : pub.getAutores()){
            if(colaborador.equals(autor)){
                autorEstaNaPublicacao = true;
                break;
            }
        }
        
        if(!autorEstaNaPublicacao){
            pub.getAutores().add(autor);
            autor.getProducoes().add(pub);
        }
        
        return pub;
    }

    /**
     * Remove uma relação entre um colaborador a uma publicação
     * @param idPublicacao
     * @param idAutor
     * @return 
     */
    public Publicacao remAutor(int idPublicacao, int idAutor){
        Publicacao pub = buscar(idPublicacao);
        if(pub == null)
            return null;
        ColaboradorDAO colaboradordao = new ColaboradorDAO();
        Colaborador autor = colaboradordao.buscar(idAutor);
        
        if(autor == null)
            return pub;
        
        boolean autorEstaNaPublicacao = false;
        for(Colaborador colaborador : pub.getAutores()){
            if(colaborador.equals(autor)){
                autorEstaNaPublicacao = true;
                break;
            }
        }
        
        if(autorEstaNaPublicacao){
            pub.getAutores().remove(autor);
            autor.getProducoes().remove(pub);
        }
        
        return pub;
    }
    
    /**
     * Remover Publicação da Memória pelo identificador
     *
     * @param id
     * @return
     */
    public Publicacao buscar(int id) {
        List<Publicacao> publicacoes = memoria.getPublicacoes();
        Publicacao obj = null;
        for (Publicacao o : publicacoes) {
            if (o.getIdentificador() == id) {
                obj = o;
                break;
            }
        }
        return obj;
    }

    /**
     * Lista as publicações existentes na mémoria
     *
     * @return
     */
    public List<Publicacao> listar() {
        List<Publicacao> publicacoes = memoria.getPublicacoes();
        return publicacoes;
    }
    
    /**
     * Buscar numero total de publicações
     * @return 
     */
    public int buscarTotal(){
        List<Publicacao> publicacoes = memoria.getPublicacoes();
        return publicacoes.size();
    }

}
