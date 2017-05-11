package com.ontology.mavenproject1;

import com.hp.hpl.jena.iri.impl.Main;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import com.reasoner.ReasonerOntologia;
import com.utils.Conexao;
import com.xml.GerencialProducao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Pedro Ivo
 */
@Path("grafo_producao")
public class GrafoProducaoResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String retornaQueryGrafo() throws SQLException{
        return retornaQueryGrafoOntologia();
    }
    
    //Retorna a query para construcao do grafo gerencial baseado nos individuos da ontologia
    private String retornaQueryGrafoOntologia() throws SQLException{
       
        String create = "";
        String relation = "";
        
        //Variaveis
        //Model model;
        //Query query;
        //String queryString;
        //Carregando arquivo .owl
        //FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        
        //ReasonerOntologia ro = new ReasonerOntologia();
        //model = ro.getInfModel();
               
        java.sql.ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        Statement stm = conexao.createStatement();
        String sql = "select\n" +
"		a.id_animal,\n" +
"		a.data_nascimento,\n" +
"		a.id_composicao_racial,\n" +
"		a.id_rebanho,\n" +
"		a.nome_animal,\n" +
"		a.sexo,\n" +
"		a.pais_origem,\n" +
"		a.id_raca,\n" +
"		a.id_animal_mae,\n" +
"		a.id_animal_pai,\n" +
"		p.id_parto,\n" +
"		p.data_parto,\n" +
"		p.id_tipo_parto,\n" +
"		p.id_escore_corporal,\n" +
"		p.id_animal_pai as touro_acasalou,\n" +
"		p.id_animal as filho_vaca,\n" +
"		p.ordem_parto,\n" +
"		p.numero_crias,\n" +
"		p.intervalo_entre_parto,\n" +
"		p.dias_seca,\n" +
"		l.id_lactacao,\n" +
"		l.data_encerramento,\n" +
"		l.producao_acumulada_leite,\n" +
"		l.producao_acumulada_proteina,\n" +
"		l.producao_acumulada_gordura,\n" +
"		l.producao_ajustada_305,\n" +
"		l.media_ccs	\n" +
"\n" +
"	from animal a left join parto p on a.id_animal = p.id_animal left join lactacao l on l.id_parto = p.id_parto\n" +
"\n" +
"	where data_eliminacao is null and p.id_animal_pai is not null and p.id_animal is not null \n" +
"	and (vivo is null or vivo is true)" +
//"       order by a.id_animal LIMIT 100";
"       order by a.id_animal";                
        
        rs = stm.executeQuery(sql);
        
        while(rs.next()){
            //Criando o no do tipo animal, caso nao exista
            create += "MERGE ( :Animal {"
                    + " nome :'animal"+rs.getString("id_animal")+"',"
                    + " data_nascimento :'"+rs.getString("data_nascimento")+"',"
                    + " rebanho :'"+rs.getString("id_rebanho")+"',"
                    + " sexo : '"+rs.getString("sexo")+"',"
                    + " pais_origem : '"+rs.getString("pais_origem")+"',"
                    + " raca : '"+rs.getString("id_raca")+"'"
                    + "})\n";
            //Criando o no do tipo parto com dados da lactacao
            create += "MERGE ( :Parto {"
                    + " nome :'parto"+rs.getString("id_parto")+"',"
                    + " data_inicio :'"+rs.getString("data_parto")+"',"
                    + " tipo_parto :'"+rs.getString("id_tipo_parto")+"',"
                    + " escore_corporal : '"+rs.getString("id_escore_corporal")+"',"
                    + " ordem_parto : '"+rs.getString("ordem_parto")+"',"
                    + " crias : '"+rs.getString("numero_crias")+"',"
                    + " intervalo_entre_parto : '"+rs.getString("intervalo_entre_parto")+"',"
                    + " dias_seca : '"+rs.getString("dias_seca")+"',"
                    + " data_encerramento : '"+rs.getString("data_encerramento")+"',"
                    + " leite : '"+rs.getString("producao_acumulada_leite")+"',"
                    + " proteina : '"+rs.getString("producao_acumulada_proteina")+"',"
                    + " gordura : '"+rs.getString("producao_acumulada_gordura")+"',"
                    + " p_305 : '"+rs.getString("producao_ajustada_305")+"',"
                    + " media_ccs : '"+rs.getString("media_ccs")+"'"
                    + "})\n";
                    //Relacionando o parto com os animais
                    //-> Touros vao ter os partos vazios; Vacas tem o parto + filhote
                    //-> Relaciono a vaca com o parto e o parto com o filhote;
            
                    String peso = rs.getString("producao_acumulada_leite");
                    if(peso == null || peso.equals("null")) peso = "0";
                    //Relacao da vaca com o parto (Av)->(P);
                    relation += "MERGE (:Animal{nome:'animal"+rs.getString("id_animal")+"'})-[:POSSUI {producao :"+peso+"}]"
                            + "->(:Parto{nome:'parto"+rs.getString("id_parto")+"'})\n";
                    //Relacao do Parto com o filho (P)->(Af);
                    relation += "MERGE (:Parto{nome:'parto"+rs.getString("id_parto")+"'})-[:POSSUI {producao :"+peso+"}]"
                            + "->(:Animal{nome:'animal"+rs.getString("filho_vaca")+"'})\n";
                    //Relacao do Touro com o parto (At)->(P)
                    relation += "MERGE (:Animal{nome:'animal"+rs.getString("touro_acasalou")+"'})-[:POSSUI {producao :"+peso+"}]"
                            + "->(:Parto{nome:'parto"+rs.getString("id_parto")+"'})\n";
                    
                    System.out.println("\nAnimal -->>"+rs.getString("id_animal"));
        }
        //create = create.substring(0, create.length()-2);
        return create+relation;
    }
}
