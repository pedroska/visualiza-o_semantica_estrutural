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
        model = FileManager.get().loadModel(
                "C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\vaquinha_importacao.owl");
        
        //Montando a string de query em sparql
        //Query para obter as fazendas relacionadas com todos os produtores cadastrados
        queryString = "PREFIX rdf: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"                      SELECT  *\n" +
"                      WHERE{\n" +
"                       ?vaca rdf:hasProduction ?lactacao .\n" +
"                      	?lactacao rdf:hasDairyControl ?controle .\n" +
"               	OPTIONAL{ ?controle rdf:ValorCCSLeite ?ccsAtual . }\n" +
"                      	OPTIONAL{ ?controle rdf:ValorCCSAnteriorLeite ?ccsAnterior . }\n" +
"                      	OPTIONAL{ ?controle rdf:ValorAcumuladoLeite ?leite . }\n" +
"                       OPTIONAL{ ?controle rdf:ValorGorduraLeite ?percentGord . }\n" +
"                       OPTIONAL{ ?controle rdf:ValorProteinaLeite ?percentProt . }\n" +
"                       ?vaca rdf:NomeVaca ?nomeVaquinha\n" +
"                      }";
        //Exibe todos os resultados obtidos com a query
        //Criação e execução da query para explorar os resultados em seguida
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(!((String)soln.getLiteral("nomeVaquinha").getValue()).equals("null")
                        && !(soln.getLiteral("leite") == null)
                        && !(soln.getLiteral("ccsAtual") == null)
                        && !(soln.getLiteral("ccsAnterior") == null)
                        && !(soln.getLiteral("percentGord") == null)
                        && !(soln.getLiteral("percentProt") == null)){
                    controles.add(new ControleLeiteiro(
                                        //(XSDDateTime)soln.getLiteral("dataControle").getValue(),
                                        /*((String)soln.getLiteral("leite").getValue()).equals("null") ? null : Float.parseFloat((String)soln.getLiteral("leite").getValue()),
                                        ((String)soln.getLiteral("ccsAtual").getValue()).equals("null") ? null : Integer.parseInt((String)soln.getLiteral("ccsAtual").getValue()), 
                                        ((String)soln.getLiteral("ccsAnterior").getValue()).equals("null") ? null : Integer.parseInt((String)soln.getLiteral("ccsAnterior").getValue()),
                                        ((String)soln.getLiteral("percentGord").getValue()).equals("null") ? null : Float.parseFloat((String)soln.getLiteral("percentGord").getValue()),
                                        ((String)soln.getLiteral("percentProt").getValue()).equals("null") ? null : Float.parseFloat((String)soln.getLiteral("percentProt").getValue()),
                                        (String)soln.getLiteral("nomeVaquinha").getValue())*/
                                        (Float)soln.getLiteral("leite").getValue(),
                                        (Integer)soln.getLiteral("ccsAtual").getValue(), 
                                        (Integer)soln.getLiteral("ccsAnterior").getValue(),
                                        (Float)soln.getLiteral("percentGord").getValue(),
                                        (Float)soln.getLiteral("percentProt").getValue(),
                                        (String)soln.getLiteral("nomeVaquinha").getValue())
                                    );
                }
            }
        }
        
        return controles;
    }
}