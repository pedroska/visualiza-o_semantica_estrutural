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
import com.xml.GerencialProducao;
import java.sql.SQLException;
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
@Path("grafo_gerencial")
public class GrafoGerencialResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String retornaQueryGrafo() throws SQLException{
        return retornaQueryGrafoOntologia();
    }
    
    //Retorna a query para construção do grafo gerencial baseado nos indivíduos da ontologia (Neo4j)
    private String retornaQueryGrafoOntologia() throws SQLException{
       
        String output = "";
        
        //Variáveis
        Model model;
        Query query;
        String queryString;
        //Carregando arquivo .owl
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        
        ReasonerOntologia ro = new ReasonerOntologia();
        model = ro.getInfModel();
        
        //Adicionando todos os indivíduos de Núcleos
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
                    "SELECT  ?nome_nucleo \n" +
                    "WHERE{\n" +
                    "   ?nucleo ont:NomeNucleo ?nome_nucleo.\n"+
                    "}\n";
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(soln.getLiteral("nome_nucleo")!= null){
                    String nome = (String)soln.getLiteral("nome_nucleo").getValue();
                    output += "CREATE ("+nome+" :Nucleo {nome :'"+nome+"'})\n";
                }
            }
        }
        
        //Adicionando todos os indivíduos de Coordenadores
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
                    "SELECT  ?nome_coord \n" +
                    "WHERE{\n" +
                    "   ?coordenador ont:NomeCoordenador ?nome_coord.\n"+
                    "}\n";
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(soln.getLiteral("nome_coord")!= null){
                    String nome = (String)soln.getLiteral("nome_coord").getValue();
                    output += "CREATE ("+nome+" :Coordenador {nome :'"+nome+"'})\n";
                }
            }
        }
        
        //Adicionando todos os indivíduos de Consultores
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
                    "SELECT  ?nome_consultor \n" +
                    "WHERE{\n" +
                    "   ?consultor ont:NomeConsultor ?nome_consultor.\n"+
                    "}\n";
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(soln.getLiteral("nome_consultor")!= null){
                    String nome = (String)soln.getLiteral("nome_consultor").getValue();
                    output += "CREATE ("+nome+" :Consultor {nome :'"+nome+"'})\n";
                }
            }
        }
        
        //Adicionando todos os indivíduos de Fazendas
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
                    "SELECT  ?nome_fazenda \n" +
                    "WHERE{\n" +
                    "   ?fazenda ont:NomeFazenda ?nome_fazenda.\n"+
                    "}\n";
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(soln.getLiteral("nome_fazenda")!= null){
                    String nome = (String)soln.getLiteral("nome_fazenda").getValue();
                    output += "CREATE ("+nome+" :Fazenda {nome :'"+nome+"'})\n";
                }
            }
        }
        
        //Adicionando as arestas / associações
        output += "CREATE\n";
        
        //Adicionando as arestas entre consultores e fazendas
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"SELECT  ?nome_consultor ?nome_fazenda (SUM(?leite_acumulado) as ?total_producao)\n" +
"WHERE{\n" +
"       ?nucleo ont:hasCoordinator ?coordenador. ?nucleo ont:regionalCenterManages ?consultor.  "
                + "?consultor ont:consultsFarm ?fazenda.  "
                + "?fazenda ont:hasHerd ?rebanho.  ?rebanho ont:hasAnimal ?vaca.\n" +
"	{SELECT ?vaca (MAX(?leite) as ?leite_acumulado)\n" +
"	 WHERE{?vaca ont:cowHasDairyControl ?controle. ?controle ont:ValorAcumuladoLeite ?leite.}\n" +
"	GROUP BY ?vaca" +
"	}\n" +
"       ?nucleo ont:NomeNucleo ?nome_nucleo. ?coordenador ont:NomeCoordenador ?nome_coordenador. ?consultor ont:NomeConsultor ?nome_consultor. ?fazenda ont:NomeFazenda ?nome_fazenda." +
"}\n" +
"GROUP BY ?nome_consultor ?nome_fazenda";
        
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(soln.getLiteral("nome_fazenda")!= null && soln.getLiteral("nome_consultor")!= null){
                    String ind1 = (String)soln.getLiteral("nome_consultor").getValue();
                    String ind2 = (String)soln.getLiteral("nome_fazenda").getValue();
                    Float valor = (Float)soln.getLiteral("total_producao").getValue();
                    output += "("+ind1+")-[:CONSULTA {producao :"+valor+"}]->("+ind2+"),\n";
                }
            }
        }
        
        //Adicionando as arestas entre coordenadores e consultores
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"SELECT  ?nome_coordenador ?nome_consultor (SUM(?leite_acumulado) as ?total_producao)\n" +
"WHERE{\n" +
"       ?nucleo ont:hasCoordinator ?coordenador. ?nucleo ont:regionalCenterManages ?consultor.  "
                + "?consultor ont:consultsFarm ?fazenda.  "
                + "?fazenda ont:hasHerd ?rebanho.  ?rebanho ont:hasCow ?vaca.\n" +
"	{SELECT ?vaca (MAX(?leite) as ?leite_acumulado)\n" +
"	 WHERE{?vaca ont:cowHasDairyControl ?controle. ?controle ont:ValorAcumuladoLeite ?leite.}\n" +
"	GROUP BY ?vaca" +
"	}\n" +
"       ?nucleo ont:NomeNucleo ?nome_nucleo. ?coordenador ont:NomeCoordenador ?nome_coordenador. ?consultor ont:NomeConsultor ?nome_consultor. ?fazenda ont:NomeFazenda ?nome_fazenda." +
"}\n" +
"GROUP BY ?nome_coordenador ?nome_consultor";
        
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(soln.getLiteral("nome_coordenador")!= null && soln.getLiteral("nome_consultor")!= null){
                    String ind1 = (String)soln.getLiteral("nome_consultor").getValue();
                    String ind2 = (String)soln.getLiteral("nome_coordenador").getValue();
                    Float valor = (Float)soln.getLiteral("total_producao").getValue();
                    output += "("+ind2+")-[:COORDENA {producao :"+valor+"}]->("+ind1+"),\n";
                }
            }
        }
        
        //Adicionando as arestas entre nucleos e coordenadores
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"SELECT  ?nome_nucleo ?nome_coordenador (SUM(?leite_acumulado) as ?total_producao)\n" +
"WHERE{\n" +
"       ?nucleo ont:possui ?coordenador. ?nucleo ont:nucleoGerencia ?consultor.  ?consultor ont:consulta ?fazenda.  "
                + "?fazenda ont:hasRebanho ?rebanho.  ?rebanho ont:hasVaca ?vaca.\n" +
"	{SELECT ?vaca (MAX(?leite) as ?leite_acumulado)\n" +
"	 WHERE{?vaca ont:vacaTemControleLeiteiro ?controle. ?controle ont:ValorAcumuladoLeite ?leite.}\n" +
"	GROUP BY ?vaca" +
"	}\n" +
"       ?nucleo ont:NomeNucleo ?nome_nucleo. ?coordenador ont:NomeCoordenador ?nome_coordenador. ?consultor ont:NomeConsultor ?nome_consultor. ?fazenda ont:NomeFazenda ?nome_fazenda." +
"}\n" +
"GROUP BY ?nome_nucleo ?nome_coordenador";
        
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(soln.getLiteral("nome_coordenador")!= null && soln.getLiteral("nome_nucleo")!= null){
                    String ind1 = (String)soln.getLiteral("nome_coordenador").getValue();
                    String ind2 = (String)soln.getLiteral("nome_nucleo").getValue();
                    Float valor = (Float)soln.getLiteral("total_producao").getValue();
                    output += "("+ind2+")-[:POSSUI {producao :"+valor+"}]->("+ind1+"),\n";
                }
            }
        }        
        
        /*Query BASE ----------------------------
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"SELECT  ?nome_nucleo ?nome_coordenador ?nome_consultor ?nome_fazenda (SUM(?leite_acumulado) as ?total_producao)\n" +
"WHERE{\n" +
"       ?nucleo ont:possui ?coordenador. ?nucleo ont:nucleoGerencia ?consultor.  ?consultor ont:consulta ?fazenda.  "
                + "?fazenda ont:hasRebanho ?rebanho.  ?rebanho ont:hasVaca ?vaca.\n" +
"	{SELECT ?vaca (MAX(?leite) as ?leite_acumulado)\n" +
"	 WHERE{?vaca ont:vacaTemControleLeiteiro ?controle. ?controle ont:ValorAcumuladoLeite ?leite.}\n" +
"	GROUP BY ?vaca" +
"	}\n" +
"       ?nucleo ont:NomeNucleo ?nome_nucleo. ?coordenador ont:NomeCoordenador ?nome_coordenador. ?consultor ont:NomeConsultor ?nome_consultor. ?fazenda ont:NomeFazenda ?nome_fazenda." +
"}\n" +
"GROUP BY ?nome_nucleo ?nome_coordenador ?nome_consultor ?nome_fazenda";
        */
        output = output.substring(0, output.length()-2);
        return output;
    }
    
    //Retorna a query para construção do grafo gerencial baseado nos dados crus do BD
    private String retornaQueryGrafoSQL() throws SQLException{
        return "sql";
    }
}
