package com.xml;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro Ivo
 */

@XmlRootElement
public class ProducaoRebanho {
    private String nome_rebanho;
    private Float producao_total;
    private Calendar ultimo_controle;

    public ProducaoRebanho() {
    }    
    
    public ProducaoRebanho(String nome_rebanho, Float producao_total, Calendar ultimo_controle) {
        this.nome_rebanho = nome_rebanho;
        this.producao_total = producao_total;
        this.ultimo_controle = ultimo_controle;
    }

    public String getNome_rebanho() {
        return nome_rebanho;
    }

    public void setNome_rebanho(String nome_rebanho) {
        this.nome_rebanho = nome_rebanho;
    }

    public Float getProducao_total() {
        return producao_total;
    }

    public void setProducao_total(Float producao_total) {
        this.producao_total = producao_total;
    }

    public Calendar getData_ultimo() {
        return ultimo_controle;
    }

    public void setData_ultimo(Calendar ultimo_controle) {
        this.ultimo_controle = ultimo_controle;
    }
}
