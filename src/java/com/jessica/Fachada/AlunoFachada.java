/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import com.jessica.DAO.AlunoDAO;
import com.jessica.DAO.UsuarioDAO;
import com.jessica.Modelo.Aluno;
import com.jessica.Modelo.RegimeCurso;
import com.jessica.Modelo.TipoAluno;
import com.jessica.Modelo.TipoUsuario;
import com.jessica.Modelo.Usuario;
import java.util.Date;

/**
 *
 * @author Jessica
 */
public class AlunoFachada extends Fachada {

    public Aluno cadastrarAluno(String nome, String email, String tipoAluno, int idOrientador, Date dataIngresso, String regimeCurso, String loginUsuario, String senhaUsuario, String tipoUsuario) {
        Aluno aluno = null;
        AlunoDAO dao = new AlunoDAO();
        UsuarioDAO usdao = new UsuarioDAO();
        Usuario usuario = null;
        try {
            TipoUsuario tipoUser = TipoUsuario.valueOf(tipoUsuario);
            usuario = usdao.addUsuario(email, email, tipoUser);
            
            TipoAluno tipoAl = TipoAluno.valueOf(tipoAluno);
            RegimeCurso regime = RegimeCurso.valueOf(regimeCurso);
            
            aluno = dao.addAluno(nome, email, tipoAl, idOrientador, dataIngresso, regime, usuario.getIdentificador());
            
        }catch(IllegalArgumentException ilg){
            if(usuario != null){
                usdao.remUsuario(usuario.getIdentificador());
            }
            return null;
        }
        
        return aluno;
    }
}
