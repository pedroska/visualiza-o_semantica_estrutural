package com.ontology.mavenproject1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.hp.hpl.jena.iri.impl.Main;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;


/**
 *
 * @author Pedro Ivo
 */
@Path("fazenda")
public class FazendasResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sparqlQuery(){
        //Variáveis
        Model model;
        Query query;
        String queryString;
        //Carregando arquivo .owl
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        model = FileManager.get().loadModel("C:\\Users\\Pedro Ivo\\Documents\\Mestrado\\Banco de Dados\\vaquinha2.owl");
        
        //Montando a string de query em sparql
        //Query para obter as fazendas relacionadas com todos os produtores cadastrados
        queryString = "PREFIX rdf: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" 
                    + "SELECT   ?fazenda\n"
                    + "WHERE{\n"
                    + "	?produtor rdf:hasFarm ?fazenda .\n" 
                    + "}\n"
                    + "ORDER BY  (?fazenda)" ;

        //Exibe todos os resultados obtidos com a query
        //Criação e execução da query para explorar os resultados em seguida
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                Resource fazenda = soln.getResource("fazenda");
                System.out.println(fazenda);
                return fazenda.getLocalName();
            }
        }
        
        return "fim da execução";
    }
    
    //Não funciona 
    /*
    private ResultSet executaQuery(String queryString){
        //Variáveis
        Model model;
        Query query;
        ResultSet results;
        //Carregando arquivo .owl
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        model = FileManager.get().loadModel("C:\\Users\\Pedro Ivo\\Documents\\Mestrado\\Banco de Dados\\vaquinha2.owl");
        
        //Criação e execução da query para explorar os resultados em seguida
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            results = qexec.execSelect();
        }
        return results;        
    }*/
}
