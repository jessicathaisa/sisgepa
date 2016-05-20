/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Modelo;

/**
 *
 * @author Jessica
 */
public class Professor extends Colaborador {

    public Professor() {
        tipoColaborador = TipoColaborador.PROFESSOR;
    }
    
    public Professor copiar() {
        Professor novo = new Professor();
        novo.setIdentificador(this.getIdentificador());
        novo.setNome(this.getNome());
        novo.setEmail(this.getEmail());

        novo.setUsuario(this.getUsuario().copiar());

        for (Projeto p : this.getProjetos()) {
            novo.getProjetos().add(p.copiar());
        }
        for (ProducaoAcademica p : this.getProducoes()) {
            if (p instanceof Publicacao) {
                novo.getProducoes().add(((Publicacao) p).copiar());
            }
            if (p instanceof Orientacao) {
                novo.getProducoes().add(((Orientacao) p).copiar());
            }
        }
        return novo;
    }

    public Professor copiaSimples() {
        Professor novo = new Professor();
        novo.setIdentificador(this.getIdentificador());
        novo.setNome(this.getNome());
        novo.setEmail(this.getEmail());

        novo.setUsuario(this.getUsuario().copiar());

        return novo;
    }

}
