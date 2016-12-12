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
public class Consultor {
    private static String todos = "";
    
    public String buscaConsultores() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT * FROM entidade WHERE id_tipo_entidade = 8";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%60s \n","id_entidade","nome_entidade");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%60s \n",rs.getString("id_entidade"),rs.getString("nome_entidade")));
        }
        
        return todos;
    }
    
    public String buscaFazendasConsultadas() throws SQLException{
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String faz;
        String sql = "SELECT entidade.id_entidade, fhe.id_fazenda FROM entidade "
                    +"INNER JOIN fazenda_has_entidade fhe on entidade.id_entidade = fhe.id_entidade\n"
                    +"WHERE id_tipo_entidade = 8";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        faz = String.format("%10s;%60s \n","id_entidade","id_fazenda");
        
        while(rs.next()){
            faz = faz.concat(String.format("%10s;%60s \n",rs.getString("id_entidade"),rs.getString("id_fazenda")));
        }
        
        return faz;
    }
}
