package com.ontology.mavenproject1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.hp.hpl.jena.iri.impl.Main;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import javax.ws.rs.PathParam;

/**
 *
 * @author Pedro Ivo
 */
@Path("/cria_regra/{ind1}/{relacao}/{ind2}")
public class CriaRegra {
    
@GET
@Produces({MediaType.TEXT_PLAIN})
public String relacao(@PathParam("ind1") String ind1, @PathParam("relacao") String relacao, @PathParam("ind2") String ind2){
        //Variaveis
        String path ="C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\";
        Model model;
        
        //Carregando arquivo .owl
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        //model = FileManager.get().loadModel(
        //        "C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\vaquinha_importacao.owl");
        
        //uri da ontologia
        String baseURI = "http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#";
        
        //inicia a maquina de inferencia e carrega a ontologia passada nela
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        java.nio.file.Path input = Paths.get(path, "vaquinha_teste.owl");
        ontModel.read(input.toUri().toString(), "RDF/XML");
        
        //Prepara as classes criadas
        OntClass domain = ontModel.createClass(baseURI + ind1);
        OntClass range = ontModel.createClass(baseURI + ind2);
        //Prepara todas as objectsProperties da ontologia
        ObjectProperty objProperty = ontModel.createObjectProperty(baseURI + relacao);
        objProperty.setDomain(domain);
        objProperty.setRange(range);
        
        FileWriter arquivo = null;
        try {
            //caminho para o novo arquivo de ontologia
            arquivo = new FileWriter(path+"vaquinha_teste.owl");
            //se não tiver o arquivo vai criar, senão será reescrito
        } catch (IOException ex) {
        }
        
        BufferedWriter out = new BufferedWriter(arquivo);
        //utilizar RDF/XML-ABBREV, so RDF/XML da erro no protege!
        ontModel.write(out, "RDF/XML");
        
        return "Sucesso!";
    }
}
