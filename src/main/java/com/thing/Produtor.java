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
public class Produtor {
    private static String todos = "";
    
    public String buscaProdutores() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM produtor";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%80s \n","id_produtor","nome_produtor");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%80s \n",rs.getString("id_produtor"),rs.getString("nome")));
        }
        
        return todos;
    }
}
