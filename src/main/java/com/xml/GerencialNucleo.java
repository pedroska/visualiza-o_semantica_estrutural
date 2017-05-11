package com.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro Ivo
 */

@XmlRootElement
public class GerencialNucleo {
    
    private String nome_nucleo;
    private String nome_coordenador;
    private String nome_consultor;

    public GerencialNucleo(){
    }
    
    public GerencialNucleo(String nome_nucleo, String nome_coordenador, String nome_consultor) {
        this.nome_nucleo = nome_nucleo;
        this.nome_coordenador = nome_coordenador;
        this.nome_consultor = nome_consultor;
    }

    public String getNome_nucleo() {
        return nome_nucleo;
    }

    public void setNome_nucleo(String nome_nucleo) {
        this.nome_nucleo = nome_nucleo;
    }

    public String getNome_coordenador() {
        return nome_coordenador;
    }

    public void setNome_coordenador(String nome_coordenador) {
        this.nome_coordenador = nome_coordenador;
    }

    public String getNome_consultor() {
        return nome_consultor;
    }

    public void setNome_consultor(String nome_consultor) {
        this.nome_consultor = nome_consultor;
    }   
}