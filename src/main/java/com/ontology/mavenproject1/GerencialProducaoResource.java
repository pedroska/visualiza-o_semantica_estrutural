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
import com.xml.GerencialProducao;
import com.xml.ProducaoRebanho;
import java.util.Date;
/**
 *
 * @author Pedro Ivo
 */
@Path("gerencial_producao")
public class GerencialProducaoResource {
        
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<GerencialProducao> QueryGerencialProducao(){
        //Variáveis
        List<GerencialProducao> nucleos = new ArrayList<>();
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
"SELECT  ?nome_nucleo ?nome_coordenador ?nome_consultor ?nome_fazenda ?nome_rebanho (SUM(?leite_acumulado) as ?total_producao)\n" +
"WHERE{\n" +
"       ?nucleo ont:possui ?coordenador. ?coordenador ont:coordena ?consultor.  ?consultor ont:consulta ?fazenda .  ?fazenda ont:hasRebanho ?rebanho.  ?rebanho ont:hasVaca ?vaca. \n" +
"	{SELECT ?vaca (MAX(?leite) as ?leite_acumulado)\n" +
"	 WHERE{?vaca ont:hasProducao ?producao.   ?producao ont:hasControleLeiteiro ?controle. ?controle ont:ValorAcumuladoLeite ?leite.}\n" +
"	GROUP BY ?vaca\n" +
"	}\n" +
"       ?nucleo ont:NomeNucleo ?nome_nucleo. ?coordenador ont:NomeCoordenador ?nome_coordenador. ?consultor ont:NomeConsultor ?nome_consultor. ?fazenda ont:NomeFazenda ?nome_fazenda. ?rebanho ont:NomeRebanho ?nome_rebanho.\n" +
"} \n" +
"GROUP BY ?nome_nucleo ?nome_coordenador ?nome_consultor ?nome_fazenda ?nome_rebanho";*/
        
        queryString = "PREFIX ont: <http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#>\n" +
"SELECT  ?nome_nucleo ?nome_coordenador ?nome_consultor ?nome_fazenda ?nome_rebanho (SUM(?leite_acumulado) as ?total_producao)\n" +
"WHERE{\n" +
"       ?nucleo ont:hasCoordinator ?coordenador. ?nucleo ont:regionalCenterManages ?consultor.  "
                + "?consultor ont:consultsFarm ?fazenda.  "
                + "?fazenda ont:hasHerd ?rebanho.  ?rebanho ont:hasAnimal ?vaca.\n" +
"	{SELECT ?vaca (MAX(?leite) as ?leite_acumulado)\n" +
"	 WHERE{?vaca ont:cowHasDairyControl ?controle. ?controle ont:ValorAcumuladoLeite ?leite.}\n" +
"	GROUP BY ?vaca" +
"	}\n" +
"       ?nucleo ont:NomeNucleo ?nome_nucleo. ?coordenador ont:NomeCoordenador ?nome_coordenador. ?consultor ont:NomeConsultor ?nome_consultor. ?fazenda ont:NomeFazenda ?nome_fazenda. ?rebanho ont:NomeRebanho ?nome_rebanho." +
"}\n" +
"GROUP BY ?nome_nucleo ?nome_coordenador ?nome_consultor ?nome_fazenda ?nome_rebanho";
        
        
        //Exibe todos os resultados obtidos com a query
        //Criação e execução da query para explorar os resultados em seguida
        query = QueryFactory.create(queryString);
        try(QueryExecution qexec = QueryExecutionFactory.create(query,model)){
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ){
                QuerySolution soln = results.nextSolution();
                if(!((String)soln.getLiteral("nome_nucleo").getValue()).equals("null")){
                    nucleos.add(new GerencialProducao(
                                        (String)soln.getLiteral("nome_nucleo").getValue(),
                                        (String)soln.getLiteral("nome_coordenador").getValue(),
                                        (String)soln.getLiteral("nome_consultor").getValue(),
                                        (Float)soln.getLiteral("total_producao").getValue()
                                    ));
                }
            }
        }
        
        return nucleos;
    }
}
