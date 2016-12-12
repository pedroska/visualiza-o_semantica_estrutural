package com.thing;

import com.utils.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Pedro Ivo
 */
public class Coordenador {
    private static String todos = "";
    
    public String buscaCoordenadores() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM entidade WHERE id_tipo_entidade = 15";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%60s \n","id_entidade","nome_entidade");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%60s \n",rs.getString("id_entidade"),rs.getString("nome_entidade")));
        }
        
        return todos;
    }
    
    public String buscaConsultoresCoordenados() throws SQLException{
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String consult;
        String sql = "SELECT coord.id_entidade as id_coordenador, consult.id_entidade as id_consultor "
                    +"FROM entidade coord " 
                    +"INNER JOIN entidade consult ON coord.id_tipo_entidade = 15 and "
                    +"consult.id_tipo_entidade = 8 and consult.id_associacao = coord.id_entidade";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        consult = String.format("%10s;%60s \n","id_coordenador","id_consultor");
        
        while(rs.next()){
            consult = consult.concat(String.format("%10s;%60s \n",rs.getString("id_coordenador"),rs.getString("id_consultor")));
        }
        
        return consult;
    }
}
