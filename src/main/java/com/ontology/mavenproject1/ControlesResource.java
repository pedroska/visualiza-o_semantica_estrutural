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


/**
 *
 * @author Pedro Ivo
 */
@Path("controle")
public class ControlesResource {
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<ControleLeiteiro> QueryControle(){
        //VariÃ¡veis
        List<ControleLeiteiro> controles = new ArrayList<>();
        //controles.add(new ControleLeiteiro((float) 800, 200, 200, (float) 3.5, (float) 3.6));
        Model model;
        Query query;
        String queryString;
        //Carregando arquivo .owl
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        model = FileManager.get().loadModel("C:\\Users\\Pedro Ivo\\Documents\\Mestrado\\Banco de Dados\\vaquinha2.owl");
        
        //Montando a string de query em sparql
        //Query para obter as fazendas relacionadas com todos os produtores cadastrados
        queryString = "PREFIX rdf: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
                      "SELECT  *\n" +
                      "WHERE{\n" +
                      "	?vaca rdf:hasProducao ?lactacao .\n" +
                      "	?lactacao rdf:hasControleLeiteiro ?controle .\n" +
                      "	?controle rdf:ValorCCSLeite ?ccsAtual .\n" +
                      "	?controle rdf:ValorCCSAnteriorLeite ?ccsAnterior .	\n" +
                      "	?controle rdf:ValorAcumuladoLeite ?leite .\n" +
                      "	?controle rdf:DataControleLeiteiro ?dataControle .\n" +
                      "	?controle rdf:ValorGorduraLeite ?percentGord .\n" +
                      "	?controle rdf:ValorProteinaLeite ?percentProt .\n" +
                      " ?vaca rdf:NomeVaca ?nomeVaquinha" +
                      "}\n" +
                     "ORDER BY  (?vaca) (?dataControle)" ;

        //Exibe todos os resultados obtidos com a query
        //CriaÃ§Ã£o e execuÃ§Ã£o da query para explorar os resultados em seguida
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                controles.add(new ControleLeiteiro(
                                    //(XSDDateTime)soln.getLiteral("dataControle").getValue(),
                                    (float) soln.getLiteral("leite").getValue(),
                                    (int)soln.getLiteral("ccsAtual").getValue(), 
                                    (int)soln.getLiteral("ccsAnterior").getValue(),
                                    (float) soln.getLiteral("percentGord").getValue(),
                                    (float) soln.getLiteral("percentProt").getValue(),
                                    (String)soln.getLiteral("nomeVaquinha").getValue())                
                                );
            }
        }
        
        return controles;
    }
}