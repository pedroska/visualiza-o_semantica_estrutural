package com.ontology.mavenproject1;

import com.reasoner.ReasonerOntologia;
import com.thing.Animal;
import com.thing.Consultor;
import com.thing.ControleLeiteiro;
import com.thing.Coordenador;
import com.thing.Entidade;
import com.thing.Fazenda;
import com.thing.Nucleo;
import com.thing.Producao;
import com.thing.Produtor;
import com.thing.Raca;
import com.thing.Rebanho;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Pedro Ivo
 */
@Path("sql")
public class RetornoSQL {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sparqlQuery() throws SQLException{
        //Variáveis
        //String populacao;
        
        //Animal a = new Animal();
        //return a.buscaAnimais();
        
        //Consultor c = new Consultor();
        //return c.buscaConsultores();
        
        //Coordenador cord = new Coordenador();
        //return cord.buscaCoordenadores();
        
        //Entidade e = new Entidade();
        //return e.buscaEntidades();
        
        //Nucleo n = new Nucleo();
        //return n.buscaNucleos();
        
        //Rebanho r = new Rebanho();
        //return r.buscaRebanhos();
        
        //ControleLeiteiro cl = new ControleLeiteiro();
        //return cl.buscaControlesLeiteiros();
        
        //Fazenda f = new Fazenda();
        //return f.buscaFazendas();
        
        //Producao p = new Producao();
        //return p.buscaLactacoes();
        
        //Produtor p = new Produtor();
        //return p.buscaProdutores();
        
        //Raca r = new Raca();
        //return r.buscaRacas();
        
        ReasonerOntologia ro = new ReasonerOntologia();
        return ro.loadOntology();
        //return "concluido";
    }
}
