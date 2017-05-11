package com.thing;

import com.utils.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.annotation.XmlRootElement;
//import org.apache.jena.datatypes.xsd.XSDDateTime;

/**
 *
 * @author Pedro Ivo
 */

@XmlRootElement
public class ControleLeiteiro {
    //private XSDDateTime dataControle;
    private Float valorAcumuladoLeite;
    private Integer valorCCSLeite;
    private Integer valorCCSAnteriorLeite;
    private Float percentual_gordura;
    private Float percentual_proteina;
    private String identificadorVaca;

    public ControleLeiteiro() {
    }

    //public ControleLeiteiro(XSDDateTime dataControle, float valorAcumuladoLeite, int valorCCSLeite, int valorCCSAnteriorLeite, 
                            //float percentual_gordura, float percentual_proteina, String identificadorVaca) {
    public ControleLeiteiro(Float valorAcumuladoLeite, Integer valorCCSLeite, Integer valorCCSAnteriorLeite, 
                            Float percentual_gordura, Float percentual_proteina, String identificadorVaca) {
        //this.dataControle = dataControle;
        this.valorAcumuladoLeite = valorAcumuladoLeite;
        this.valorCCSLeite = valorCCSLeite;
        this.valorCCSAnteriorLeite = valorCCSAnteriorLeite;
        this.percentual_gordura = percentual_gordura;
        this.percentual_proteina = percentual_proteina;
        this.identificadorVaca = identificadorVaca;
    }
    
    public String buscaControlesLeiteiros() throws SQLException{
        String todos;
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT cl.*,cl_ant.ccs as ccs_anterior "
                    + "FROM controle_leiteiro cl "
                    + "     INNER JOIN controle_leiteiro cl_ant ON cl.id_animal = cl_ant.id_animal "
                    + "     AND cl_ant.data_controle = (select MAX(c.data_controle) from controle_leiteiro c "
                    + "         where c.id_animal = cl.id_animal AND c.data_controle < cl.data_controle AND c.ccs is not null)"
                    + "WHERE cl.data_controle > '01/01/2014' AND cl.percentual_gordura is not null";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%10s;%10s;%20s;%20s;%20s;%20s \n",
                "id_controle_leiteiro","data_controle","leite_acumulado","ccs_leite","ccs_anterior_leite","percentual_gordura",
                "percentual_proteina");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%10s;%10s;%20s;%20s;%20s;%20s \n",
                    rs.getString("id_controle_leiteiro"),rs.getString("data_controle"),rs.getString("producao_acumulada_leite"),
                    rs.getString("ccs"),rs.getString("ccs_anterior"),rs.getString("percentual_gordura"),
                    rs.getString("percentual_proteina")));
        }
        
        return todos;
    }
    
    //public XSDDateTime getDataControle() {
    //    return dataControle;
    //}

    //public void setDataControle(XSDDateTime dataControle) {
    //    this.dataControle = dataControle;
    //}

    public Float getValorAcumuladoLeite() {
        return valorAcumuladoLeite;
    }

    public void setValorAcumuladoLeite(Float valorAcumuladoLeite) {
        this.valorAcumuladoLeite = valorAcumuladoLeite;
    }

    public Integer getValorCCSLeite() {
        return valorCCSLeite;
    }

    public void setValorCCSLeite(Integer valorCCSLeite) {
        this.valorCCSLeite = valorCCSLeite;
    }

    public Integer getValorCCSAnteriorLeite() {
        return valorCCSAnteriorLeite;
    }

    public void setValorCCSAnteriorLeite(Integer valorCCSAnteriorLeite) {
        this.valorCCSAnteriorLeite = valorCCSAnteriorLeite;
    }

    public Float getPercentual_gordura() {
        return percentual_gordura;
    }

    public void setPercentual_gordura(Float percentual_gordura) {
        this.percentual_gordura = percentual_gordura;
    }

    public Float getPercentual_proteina() {
        return percentual_proteina;
    }

    public void setPercentual_proteina(Float percentual_proteina) {
        this.percentual_proteina = percentual_proteina;
    }

    public String getIdentificadorVaca() {
        return identificadorVaca;
    }

    public void setIdentificadorVaca(String identificadorVaca) {
        this.identificadorVaca = identificadorVaca;
    }
}