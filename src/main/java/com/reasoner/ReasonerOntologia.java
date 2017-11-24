package com.reasoner;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.mindswap.pellet.jena.PelletReasonerFactory;

/**
 *
 * @author Pedro Ivo
 */
public class ReasonerOntologia {
    //Falta verificar / implementar
    
    //String path ="C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\";
    //String path = "../../files/ontology/"
    //private final String inferredontology = path+"vaquinha_inferred";
    
    //variavel para acesso à ontologia com inferencia
    private InfModel infModel;

    //variavel para acesso à ontologia sem inferencia
    private OntModel ontModel;

    //caminho para a ontologia
    public static final String path = "C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\";
    public static final String ontology = path+"vaquinha_importacao.owl";
    //caminho para a ontologia inferida
    public static final String ontology_inferred = path+"vaquinha_inferred.owl";
    //uri da ontologia
    public static final String URI = "http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#";
    
    public String saida;
        
    public ReasonerOntologia(){
        if(infModel == null){
            Model model = ModelFactory.createDefaultModel();
            //model.read(ontology);
            Path input = Paths.get(path, "vaquinha_importacao.owl");
            //Path input = Paths.get(path, "vaquinhaPlusPlus.owl");
            model.read(input.toUri().toString(), "RDF/XML");

            ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
            ontModel.loadImports();
            ontModel.prepare();

            com.hp.hpl.jena.reasoner.Reasoner reasoner = PelletReasonerFactory.theInstance().create();
            infModel = ModelFactory.createInfModel(reasoner, ontModel);
            infModel.prepare();
        }
        //Gerar o novo arquivo com as inferências da ontologia
        //FileWriter arquivo = null;
        //try {
            //caminho para o novo arquivo de ontologia
        //    arquivo = new FileWriter(ontology_inferred);
            //se não tiver o arquivo vai criar, senão será reescrito
        //} catch (IOException ex) {
        //}
        
        //BufferedWriter out = new BufferedWriter(arquivo);
        //infModel.write(out, "RDF/XML-ABBREV");
        //saida = infModel.toString();
    }
    
    public Model getInfModel(){
        if(infModel == null){
            ReasonerOntologia rs = new ReasonerOntologia();
        }
        return infModel;
    }
    
    public String loadReasoner(){
        ReasonerOntologia rs = new ReasonerOntologia();
        
        return "ok";
    }
}
