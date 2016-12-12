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
public class Nucleo {
    private static String todos = "";
    
    public String buscaNucleos() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM entidade WHERE id_tipo_entidade = 14";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%80s \n","id_entidade","nome_entidade");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%80s \n",rs.getString("id_entidade"),rs.getString("nome_entidade")));
        }
        
        return todos;
    }
    
    public String buscaCoordenadores() throws SQLException{
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String coord;
        String sql = "SELECT nucleo.id_entidade as id_nucleo, coord.id_entidade as id_coordenador "
                    +"FROM entidade coord " 
                    +"INNER JOIN entidade nucleo ON coord.id_tipo_entidade = 15 and "
                    +"nucleo.id_tipo_entidade = 14 and coord.id_associacao = nucleo.id_entidade";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        coord = String.format("%10s;%60s \n","id_nucleo","id_coordenador");
        
        while(rs.next()){
            coord = coord.concat(String.format("%10s;%60s \n",rs.getString("id_nucleo"),rs.getString("id_coordenador")));
        }
        
        return coord;
    }
}
