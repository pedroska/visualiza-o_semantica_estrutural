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
public class Rebanho {
    private static String todos = "";
    
    public String buscaRebanhos() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM rebanho";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%60s \n","id_rebanho","nome_rebanho");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%60s \n",rs.getString("id_rebanho"),rs.getString("nome_rebanho")));
        }
        
        return todos;
    }
}
