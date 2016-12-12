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
public class Producao {
    private static String todos = "";
    
    public String buscaLactacoes() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM lactacao WHERE data_inicio > '01/01/2014'";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%20s;%20s\n","id_lactacao","data_inicio","data_encerramento");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%20s;%20s\n",rs.getString("id_lactacao"),rs.getString("data_inicio")
                            ,rs.getString("data_encerramento")));
        }
        
        return todos;
    }
    
    public String buscaControlesLeiteiros() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String s;
        String sql = "SELECT l.id_lactacao, cl.id_controle_leiteiro "
                    +"FROM lactacao l INNER JOIN controle_leiteiro cl on l.id_lactacao = cl.id_lactacao\n"
                    +"WHERE data_inicio > '01/01/2014' AND cl.percentual_gordura is not null";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        s = String.format("%10s;%20s\n","id_lactacao","id_controle_leiteiro");
        
        while(rs.next()){
            s = s.concat(String.format("%10s;%20s\n",rs.getString("id_lactacao"),rs.getString("id_controle_leiteiro")));
        }
        
        return s;
    }
}
