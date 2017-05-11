package com.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro Ivo
 */

@XmlRootElement
public class GerencialProducao {
    private String nome_nucleo;
    private String nome_coordenador;
    private String nome_consultor;
    private Float total_producao;

    public GerencialProducao() {
    }

    public GerencialProducao(String nome_nucleo, String nome_coordenador, String nome_consultor, Float total_producao) {
        this.nome_nucleo = nome_nucleo;
        this.nome_coordenador = nome_coordenador;
        this.nome_consultor = nome_consultor;
        this.total_producao = total_producao;
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

    public Float getTotal_producao() {
        return total_producao;
    }

    public void setTotal_producao(Float total_producao) {
        this.total_producao = total_producao;
    }
    
    
}
