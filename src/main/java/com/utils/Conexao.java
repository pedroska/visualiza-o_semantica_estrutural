package com.utils;

import java.sql.*;
import org.postgresql.Driver;

public class Conexao {
	private Connection conexao = null;
	private String url = "jdbc:postgresql://localhost:5432/gisleite";  
        private String usuario = "postgres";  
        private String senha = "Caneca01";
        
        public Conexao(){
            //System.out.println("teste");
            try 
            {
                    Class.forName("org.postgresql.Driver").newInstance();
                    conexao = DriverManager.getConnection(url, usuario, senha);
                    System.out.println("Conexão realizada com sucesso.");
            } 
            catch (Exception e) 
            {
                    e.printStackTrace();
            }	
	}
	
	public Connection getConn(){
		return this.conexao;		
	}
}
