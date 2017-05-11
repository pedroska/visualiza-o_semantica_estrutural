package com.xml;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro Ivo
 */

@XmlRootElement
public class ProducaoRebanhoData {
    
    private Float producao_total;
    private Calendar ultimo_controle;
    private Integer total_controles;

    public ProducaoRebanhoData() {
    }    
    
    public ProducaoRebanhoData(Float producao_total, Calendar ultimo_controle, Integer total_controles) {
        
        this.producao_total = producao_total;
        this.ultimo_controle = ultimo_controle;
        this.total_controles = total_controles;
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

    public Integer getTotal_controles() {
        return total_controles;
    }

    public void setTotal_controles(Integer total_controles) {
        this.total_controles = total_controles;
    }   
}