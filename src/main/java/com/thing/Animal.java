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
public class Animal {
    private static String todos = "";
    
    public String buscaAnimais() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT id_animal, nome_animal "
                    + "FROM animal WHERE sexo = 'f' AND vivo is not false AND data_nascimento > '01/01/2014'";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%50s \n","id_animal","nome_animal");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%50s \n",rs.getString("id_animal"),rs.getString("nome_animal")));
        }
        
        return todos;
    }
    
    public String buscaProducoes() throws SQLException{
        String producao;
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT a.id_animal, l.id_lactacao "
                    + "FROM animal a "
                    + "INNER JOIN lactacao l ON a.id_animal = l.id_animal "
                    + "AND l.data_inicio = (SELECT MAX(data_inicio) from lactacao "
                    + "                            WHERE lactacao.id_animal = a.id_animal)"
                    + "WHERE a.sexo = 'f' AND a.vivo is not false AND a.data_nascimento > '01/01/2014'";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        producao = String.format("%10s;%60s \n","id_animal","id_lactacao");
        
        while(rs.next()){
            producao = producao.concat(String.format("%10s;%60s \n",rs.getString("id_animal"),rs.getString("id_lactacao")));
        }
        
        return producao;
    }
    
    public String buscaRacas() throws SQLException{
        String raca;
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT a.id_animal, a.id_raca "
                    + "FROM animal a "
                    + "WHERE a.sexo = 'f' AND a.vivo is not false AND a.data_nascimento > '01/01/2014'AND a.id_raca is not null";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        raca = String.format("%10s;%60s \n","id_animal","id_lactacao");
        
        while(rs.next()){
            raca = raca.concat(String.format("%10s;%60s \n",rs.getString("id_animal"),rs.getString("id_raca")));
        }
        
        return raca;
    }
}
