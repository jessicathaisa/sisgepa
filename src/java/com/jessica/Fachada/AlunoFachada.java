/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.AlunoDAO;
import com.jessica.DAO.OrientacaoDAO;
import com.jessica.DAO.ProjetoDAO;
import com.jessica.DAO.PublicacaoDAO;
import com.jessica.DAO.UsuarioDAO;
import com.jessica.Excecao.UsuarioDuplicadoException;
import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.Orientacao;
import com.jessica.Modelo.ProducaoAcademica;
import com.jessica.Modelo.Projeto;
import com.jessica.Modelo.Publicacao;
import com.jessica.Modelo.RegimeCurso;
import com.jessica.Modelo.StatusProjeto;
import com.jessica.Modelo.TipoAluno;
import com.jessica.Modelo.TipoUsuario;
import com.jessica.Modelo.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jessica
 */
public class AlunoFachada {

    /**
     * Cadastra um aluno
     *
     * @param nome
     * @param email
     * @param tipoAluno
     * @param idOrientador
     * @param dataIngresso
     * @param regimeCurso
     * @param loginUsuario
     * @param senhaUsuario
     * @param tipoUsuario
     * @return
     * @throws UsuarioDuplicadoException
     */
    public Aluno cadastrarAluno(String nome, String email, String tipoAluno, int idOrientador, Date dataIngresso, String regimeCurso, String loginUsuario, String senhaUsuario, String tipoUsuario) throws UsuarioDuplicadoException {
        Aluno aluno = null;
        AlunoDAO dao = new AlunoDAO();
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

            TipoAluno tipoAl = TipoAluno.valueOf(tipoAluno);
            RegimeCurso regime = RegimeCurso.NAO_SE_APLICA;
            if (regimeCurso != null) {
                regime = RegimeCurso.valueOf(regimeCurso);
            }

            aluno = dao.addAluno(nome, email, tipoAl, idOrientador, dataIngresso, regime, usuario.getIdentificador());

        } catch (IllegalArgumentException ilg) {
            if (usuario != null) {
                usdao.remUsuario(usuario.getIdentificador());
            }
            return null;
        }

        return aluno;
    }

    /**
     * Editar os dados do aluno
     *
     * @param id
     * @param nome
     * @param email
     * @param tipoAluno
     * @param idOrientador
     * @param dataIngresso
     * @param regimeCurso
     * @param loginUsuario
     * @param senhaUsuario
     * @param tipoUsuario
     * @return
     * @throws UsuarioDuplicadoException
     */
    public Aluno editarAluno(int id, String nome, String email, String tipoAluno, int idOrientador, Date dataIngresso, String regimeCurso, String loginUsuario, String senhaUsuario, String tipoUsuario) throws UsuarioDuplicadoException {
        Aluno aluno = null;
        AlunoDAO dao = new AlunoDAO();
        UsuarioDAO usdao = new UsuarioDAO();
        Usuario usuario = null;

        aluno = dao.buscar(id);
        Usuario usuarioaluno = aluno.getUsuario();

        if (loginUsuario.equals(usuarioaluno.getLogin())) {
            // Não quero mudar.
            usuario = usuarioaluno;
        } else {
            // Verifica se o usuário já existe no sistema
            usuario = usdao.buscar(loginUsuario);
            if (usuario != null) {
                throw new UsuarioDuplicadoException();
            } else {
                usdao.remUsuario(usuarioaluno.getIdentificador());

                TipoUsuario tipoUser = TipoUsuario.valueOf(tipoUsuario);
                usuario = usdao.addUsuario(loginUsuario, senhaUsuario, tipoUser);
            }

        }

        // Tenta parsear os dados vindos do cliente
        try {

            TipoAluno tipoAl = TipoAluno.valueOf(tipoAluno);
            RegimeCurso regime = RegimeCurso.NAO_SE_APLICA;
            if (regimeCurso != null) {
                regime = RegimeCurso.valueOf(regimeCurso);
            }

            aluno = dao.editarAluno(id, nome, email, tipoAl, idOrientador, dataIngresso, regime);
            aluno.setUsuario(usuario);

        } catch (IllegalArgumentException ilg) {
            if (usuario != null) {
                usdao.remUsuario(usuario.getIdentificador());
            }
            return null;
        }

        return aluno;
    }

    /**
     * Apagar um aluno do sistema
     *
     * @param id
     * @return
     */
    public boolean apagarAluno(int id) {
        AlunoDAO dao = new AlunoDAO();
        ProjetoDAO projdao = new ProjetoDAO();
        PublicacaoDAO pubdao = new PublicacaoDAO();
        OrientacaoDAO oridao = new OrientacaoDAO();
        try {
            Aluno aluno = dao.buscar(id);
            List<Projeto> auxiliar = new ArrayList<>(aluno.getProjetos());
            for (Projeto projeto : auxiliar) {
                projdao.remParticipante(projeto.getIdentificador(), aluno.getIdentificador());
            }

            for (ProducaoAcademica producao : new ArrayList<>(aluno.getProducoes())) {
                if (producao instanceof Publicacao) {
                    pubdao.remAutor(producao.getIdentificador(), aluno.getIdentificador());
                } else if (producao instanceof Orientacao) {
                    Orientacao orientacao = (Orientacao) producao;
                    oridao.remOrientacao(orientacao.getAluno().getIdentificador(), orientacao.getProfessor().getIdentificador());
                }
            }

            dao.remAluno(id);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * Buscar um aluno pelo id
     *
     * @param id
     * @return
     */
    public Aluno buscarAluno(int id) {
        AlunoDAO dao = new AlunoDAO();
        Aluno a = TratarReferenciaCircular.tratar(dao.buscar(id));
        return a;
    }

    /**
     * Buscar os projetos de um aluno pelo id
     *
     * @param id
     * @return
     */
    public List<Projeto> buscarProjetosAluno(int id) {
        AlunoDAO dao = new AlunoDAO();
        Aluno a = dao.buscar(id);
        List<Projeto> projetos = TratarReferenciaCircular.tratarLista(a.getProjetos());
        return projetos;
    }
    
    /**
     * Retirna o total de projetos em andamento do aluno
     * @param id
     * @return 
     */
    public int totalProjetosEmAndamento(int id){
        List<Projeto> projetos = buscarProjetosAluno(id);
        int quantidade = 0;
        
        for(Projeto p : projetos)
            if(p.getStatus() == StatusProjeto.EM_ANDAMENTO)
                quantidade++;
        
        return quantidade;
    }

    /**
     * Buscar as producoes de um aluno pelo id
     *
     * @param id
     * @return
     */
    public List<ProducaoAcademica> buscarProducoesAluno(int id) {
        AlunoDAO dao = new AlunoDAO();
        Aluno a = dao.buscar(id);
        List<ProducaoAcademica> producoes = TratarReferenciaCircular.tratarLista(a.getProducoes());
        return producoes;
    }

    /**
     * lista os alunos existentes no sistema
     *
     * @return
     */
    public List<Aluno> listarAlunos() {
        AlunoDAO dao = new AlunoDAO();

        List<Aluno> lista = dao.listar();
        return TratarReferenciaCircular.tratarLista(lista);
    }
}
