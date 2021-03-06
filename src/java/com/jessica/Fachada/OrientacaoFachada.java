/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Pesquisador Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.OrientacaoDAO;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Modelo.Orientacao;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class OrientacaoFachada {

    /**
     * Listar as orientações
     *
     * @return
     */
    public List<Orientacao> listarOrientacoes() {
        OrientacaoDAO dao = new OrientacaoDAO();
        List<Orientacao> lista = dao.listar();
        return TratarReferenciaCircular.tratarLista(lista);
    }

    /**
     * Buscar uma orientação pelo id
     *
     * @param id
     * @return
     */
    public Orientacao buscarOrientacao(int id) {
        OrientacaoDAO dao = new OrientacaoDAO();
        Orientacao o = dao.buscar(id);

        return TratarReferenciaCircular.tratar(o);
    }

    /**
     * Buscar as orientações pelo id de aluno
     *
     * @param id
     * @return
     */
    public List<Orientacao> buscarOrientacaoPorAluno(int id) {
        OrientacaoDAO dao = new OrientacaoDAO();
        List<Orientacao> lista = dao.buscarPorAluno(id);
        return TratarReferenciaCircular.tratarLista(lista);
    }

    /**
     * Cadastrar uma orientação
     *
     * @param idAluno
     * @param idProfessor
     * @param titulo
     * @param ano
     * @return
     * @throws UsuarioDuplicadoException
     */
    public Orientacao cadastrarOrientacao(int idAluno, int idProfessor, String titulo, int ano) throws UsuarioDuplicadoException {
        OrientacaoDAO dao = new OrientacaoDAO();

        // Verifica se o usuário já existe no sistema
        Orientacao auxiliar = dao.buscar(idAluno, idProfessor);
        if (auxiliar != null) {
            throw new UsuarioDuplicadoException();
        }

        // Tenta parsear os dados vindos do cliente
        try {
            auxiliar = dao.addOrientacao(idAluno, idProfessor, titulo, ano);
        } catch (IllegalArgumentException ilg) {
        }

        return auxiliar;
    }

    /**
     * Editar uma orientação
     * @param id
     * @param idAluno
     * @param idProfessor
     * @param titulo
     * @param ano
     * @return
     * @throws UsuarioDuplicadoException
     */
    public Orientacao editarOrientacao(int id, int idAluno, int idProfessor, String titulo, int ano) throws UsuarioDuplicadoException {
        OrientacaoDAO dao = new OrientacaoDAO();

        // Verifica se a orientação já existe no sistema
        Orientacao auxiliar = dao.buscar(id);
        if (auxiliar == null) {
            return null;
        }

        // Tenta parsear os dados vindos do cliente
        if (dao.remOrientacao(auxiliar.getAluno().getIdentificador(), auxiliar.getProfessor().getIdentificador())) {
            auxiliar = dao.addOrientacao(idAluno, idProfessor, titulo, ano);
        }

        return auxiliar;
    }

    /**
     * Apagar uma orientação
     * @param id
     * @return
     */
    public boolean apagarOrientacao(int id) {
        OrientacaoDAO dao = new OrientacaoDAO();

        // Verifica se a orientação já existe no sistema
        Orientacao auxiliar = dao.buscar(id);
        if (auxiliar == null) {
            return false;
        }

        return dao.remOrientacao(auxiliar.getAluno().getIdentificador(), auxiliar.getProfessor().getIdentificador());
    }

}
