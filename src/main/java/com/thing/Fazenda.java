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
public class Fazenda {
    private static String todos = "";
    
    public String buscaFazendas() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM fazenda";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%60s \n","id_fazenda","nome_fazenda");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%60s \n",rs.getString("id_fazenda"),rs.getString("nome")));
        }
        
        return todos;
    }
}
