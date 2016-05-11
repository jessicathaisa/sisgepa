/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Pesquisador Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.AlunoDAO;
import com.jessica.DAO.OrientacaoDAO;
import com.jessica.DAO.ProfessorDAO;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.Orientacao;
import com.jessica.Modelo.Professor;
import com.jessica.Modelo.RegimeCurso;
import com.jessica.Modelo.TipoAluno;
import com.jessica.Modelo.TipoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class OrientacaoFachada extends Fachada {

    /**
     * Listar as orientações
     *
     * @return
     */
    public List<Orientacao> listarOrientacoes() {
        OrientacaoDAO dao = new OrientacaoDAO();
        List<Orientacao> auxiliar = new ArrayList<>();
        List<Orientacao> lista = dao.listar();

        for (Orientacao o : lista) {
            Orientacao aux = o.copiaSimples();
            auxiliar.add(aux);
        }

        return auxiliar;
    }

    /**
     * Buscar uma orientação pelo id
     *
     * @param id
     * @return
     */
    public Orientacao buscarOrientacao(int id) {
        OrientacaoDAO dao = new OrientacaoDAO();
        Orientacao auxiliar = dao.buscar(id);
        Orientacao o = auxiliar.copiaSimples();

        return o;
    }

    /**
     * Cadastrar uma orientação
     *
     * @param idAluno
     * @param idProfessor
     * @param titulo
     * @return
     * @throws UsuarioDuplicadoException
     */
    public Orientacao cadastrarOrientacao(int idAluno, int idProfessor, String titulo) throws UsuarioDuplicadoException {
        OrientacaoDAO dao = new OrientacaoDAO();

        // Verifica se o usuário já existe no sistema
        Orientacao auxiliar = dao.buscar(idAluno, idProfessor);
        if (auxiliar != null) {
            throw new UsuarioDuplicadoException();
        }

        // Tenta parsear os dados vindos do cliente
        try {
            auxiliar = dao.addOrientacao(idAluno, idProfessor, titulo);
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
     * @return
     * @throws UsuarioDuplicadoException
     */
    public Orientacao editarOrientacao(int id, int idAluno, int idProfessor, String titulo) throws UsuarioDuplicadoException {
        OrientacaoDAO dao = new OrientacaoDAO();

        // Verifica se a orientação já existe no sistema
        Orientacao auxiliar = dao.buscar(id);
        if (auxiliar == null) {
            return null;
        }

        // Tenta parsear os dados vindos do cliente
        if (dao.remOrientacao(auxiliar.getAluno().getIdentificador(), auxiliar.getProfessor().getIdentificador())) {
            auxiliar = dao.addOrientacao(idAluno, idProfessor, titulo);
        }

        return auxiliar;
    }

}
