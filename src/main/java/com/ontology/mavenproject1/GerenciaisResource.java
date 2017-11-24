package com.ontology.mavenproject1;

import com.thing.ControleLeiteiro;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.iri.impl.Main;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import com.xml.GerencialNucleo;


/**
 *
 * @author Pedro Ivo
 */
@Path("gerencial")
public class GerenciaisResource {
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<GerencialNucleo> QueryNucleo(){
        //Variaveis
        Model model;
        Query query;
        String queryString;
        List<GerencialNucleo> nucleos = new ArrayList<>();
        //Carregando arquivo .owl
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        model = FileManager.get().loadModel(
                "C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\vaquinha_importacao.owl");
        
        queryString = "PREFIX rdf: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"                      SELECT  *\n" +
"                      WHERE{\n" +
"                      	\n" +
"	?nucleo rdf:hasCoordinator ?coordenador .\n" +
"	?coordenador rdf:coordinatesConsultant ?consultor .\n" +
"	OPTIONAL{ ?consultor rdf:consultsFarm ?fazenda . }\n" +
"	OPTIONAL{ ?consultor rdf:NomeConsultor ?nome_consultor . }\n" +
"	OPTIONAL{ ?coordenador rdf:NomeCoordenador ?nome_coordenador . }\n" +
"	OPTIONAL{ ?nucleo rdf:NomeNucleo ?nome_nucleo . }\n" +
"\n" +
"                      }\n" +
"	ORDER BY ?nucleo ?coordenador ?consultor";
        //Exibe todos os resultados obtidos com a query
        //Criação e execução da query para explorar os resultados em seguida
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                nucleos.add(new GerencialNucleo(
                                        (String)soln.getLiteral("nome_nucleo").getValue(),
                                        (String)soln.getLiteral("nome_coordenador").getValue(),
                                        (String)soln.getLiteral("nome_consultor").getValue()
                                    ));
            }
        }
        
        return nucleos;
    }
}