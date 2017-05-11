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
import com.reasoner.ReasonerOntologia;
import com.xml.ProducaoRebanho;
import java.util.Date;
/**
 *
 * @author Pedro Ivo
 */
@Path("producao_rebanho")
public class ProducaoRebanhoResource {

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<ProducaoRebanho> QueryControle(){
        //Variáveis
        List<ProducaoRebanho> producoes = new ArrayList<>();
        Model model;
        Query query;
        String queryString;
        //Carregando arquivo .owl
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        //model = FileManager.get().loadModel(
        //        "C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\vaquinha_importacao.owl");
        
        ReasonerOntologia ro = new ReasonerOntologia();
        model = ro.getInfModel();
        //Montando a string de query em sparql
        //Query para obter as fazendas relacionadas com todos os produtores cadastrados
        /*queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"SELECT  ?nome_rebanho (SUM(?total_leite) as ?producao_rebanho) (MAX(?maior_controle) as ?ultimo_controle)\n" +
"WHERE{\n" +
"  {\n" +
"  SELECT ?nome_rebanho ?vaca (MAX(?data) as ?maior_controle) (MAX(?leite) as ?total_leite)\n" +
"  WHERE{  ?rebanho ont:hasVaca ?vaca.    ?vaca ont:hasProducao ?producao. ?producao ont:hasControleLeiteiro ?controle.  "+
"          ?controle ont:DataControleLeiteiro ?data. ?controle ont:ValorAcumuladoLeite ?leite. ?rebanho ont:NomeRebanho ?nome_rebanho.}\n" +
"  GROUP BY ?nome_rebanho ?vaca\n" +
"  }\n" +
"}\n" +
"GROUP BY ?nome_rebanho";*/
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"SELECT  ?nome_rebanho (SUM(?total_leite) as ?producao_rebanho) (MAX(?maior_controle) as ?ultimo_controle)\n" +
"WHERE{\n" +
"  {\n" +
"  SELECT ?nome_rebanho ?vaca (MAX(?data) as ?maior_controle) (MAX(?leite) as ?total_leite)\n" +
"  WHERE{  ?rebanho ont:hasVaca ?vaca.    ?vaca ont:vacaTemControleLeiteiro ?controle.  "+
"          ?controle ont:DataControleLeiteiro ?data. ?controle ont:ValorAcumuladoLeite ?leite. ?rebanho ont:NomeRebanho ?nome_rebanho.}\n" +
"  GROUP BY ?nome_rebanho ?vaca\n" +
"  }\n" +
"}\n" +
"GROUP BY ?nome_rebanho";
        //Exibe todos os resultados obtidos com a query
        //Criação e execução da query para explorar os resultados em seguida
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(!((String)soln.getLiteral("nome_rebanho").getValue()).equals("null")){
                    producoes.add(new ProducaoRebanho(
                                        (String)soln.getLiteral("nome_rebanho").getValue(),
                                        (Float)soln.getLiteral("producao_rebanho").getValue(),
                                        ((XSDDateTime)soln.getLiteral("ultimo_controle").getValue()).asCalendar()
                                    ));
                }
            }
        }
        
        return producoes;
    }
}
