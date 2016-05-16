/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jessica.Modelo;

/**
 *
 * @author Jessica
 */
public class Pesquisador extends Colaborador {

    public Pesquisador copiar() {
        Pesquisador novo = new Pesquisador();
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

    public Pesquisador copiaSimples() {
        Pesquisador novo = new Pesquisador();
        novo.setIdentificador(this.getIdentificador());
        novo.setNome(this.getNome());
        novo.setEmail(this.getEmail());

        novo.setUsuario(this.getUsuario().copiar());

        for (ProducaoAcademica p : this.getProducoes()) {
            if (p instanceof Publicacao) {
                novo.getProducoes().add(((Publicacao) p).copiaSimples());
            }
            if (p instanceof Orientacao) {
                novo.getProducoes().add(((Orientacao) p).copiaSimples());
            }
        }
        return novo;
    }
}
