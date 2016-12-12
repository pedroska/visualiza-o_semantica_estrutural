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
    private float valorAcumuladoLeite;
    private int valorCCSLeite;
    private int valorCCSAnteriorLeite;
    private float percentual_gordura;
    private float percentual_proteina;
    private String identificadorVaca;

    public ControleLeiteiro() {
    }

    //public ControleLeiteiro(XSDDateTime dataControle, float valorAcumuladoLeite, int valorCCSLeite, int valorCCSAnteriorLeite, 
                            //float percentual_gordura, float percentual_proteina, String identificadorVaca) {
    public ControleLeiteiro(float valorAcumuladoLeite, int valorCCSLeite, int valorCCSAnteriorLeite, 
                            float percentual_gordura, float percentual_proteina, String identificadorVaca) {
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
        
        String sql = "SELECT * FROM controle_leiteiro WHERE data_controle > '01/01/2014' AND percentual_gordura is not null";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%60s \n","id_controle_leiteiro","data_controle");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%60s \n",rs.getString("id_controle_leiteiro"),rs.getString("data_controle")));
        }
        
        return todos;
    }
    
    //public XSDDateTime getDataControle() {
    //    return dataControle;
    //}

    //public void setDataControle(XSDDateTime dataControle) {
    //    this.dataControle = dataControle;
    //}

    public float getValorAcumuladoLeite() {
        return valorAcumuladoLeite;
    }

    public void setValorAcumuladoLeite(float valorAcumuladoLeite) {
        this.valorAcumuladoLeite = valorAcumuladoLeite;
    }

    public int getValorCCSLeite() {
        return valorCCSLeite;
    }

    public void setValorCCSLeite(int valorCCSLeite) {
        this.valorCCSLeite = valorCCSLeite;
    }

    public int getValorCCSAnteriorLeite() {
        return valorCCSAnteriorLeite;
    }

    public void setValorCCSAnteriorLeite(int valorCCSAnteriorLeite) {
        this.valorCCSAnteriorLeite = valorCCSAnteriorLeite;
    }

    public float getPercentual_gordura() {
        return percentual_gordura;
    }

    public void setPercentual_gordura(float percentual_gordura) {
        this.percentual_gordura = percentual_gordura;
    }

    public float getPercentual_proteina() {
        return percentual_proteina;
    }

    public void setPercentual_proteina(float percentual_proteina) {
        this.percentual_proteina = percentual_proteina;
    }

    public String getIdentificadorVaca() {
        return identificadorVaca;
    }

    public void setIdentificadorVaca(String identificadorVaca) {
        this.identificadorVaca = identificadorVaca;
    }
    
    
}
