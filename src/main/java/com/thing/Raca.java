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
public class Raca {
    private static String todos = "";
    
    public String buscaRacas() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM sis_raca";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%80s \n","id_raca","nome_raca");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%80s \n",rs.getString("id_raca"),rs.getString("nome_raca")));
        }
        
        return todos;
    }
}
