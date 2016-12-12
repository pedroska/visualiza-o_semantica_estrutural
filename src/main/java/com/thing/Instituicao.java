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
public class Instituicao {
    private static String todos = "";
    
    public String buscaInstituicoes() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM entidade WHERE id_tipo_entidade IN (1,2,3,4,5,6,9,10,11,12,13)";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%60s \n","id_entidade","nome_entidade");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%60s \n",rs.getString("id_entidade"),rs.getString("nome_entidade")));
        }
        
        return todos;
    }
}
