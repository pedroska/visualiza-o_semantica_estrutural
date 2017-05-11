package com.reasoner;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        /*
        String baseURI = "http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#";
        //"C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology";
        String saida;
        
        //inicia a maquina de inferencia e carrega a ontologia passada nela
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        Path input = Paths.get(path, "vaquinha_importacao.owl");
        ontModel.read(input.toUri().toString(), "RDF/XML-ABBREV");
        
        //Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        //reasoner = reasoner.bindSchema(ontModel);
        //OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM_TRANS_INF;
        //ontModelSpec.setReasoner(reasoner);
        
        //Gerar o novo arquivo com as inferências da ontologia
        FileWriter arquivo = null;
        try {
            //caminho para o novo arquivo de ontologia
            arquivo = new FileWriter(inferredontology);
            //se não tiver o arquivo vai criar, senão será reescrito
        } catch (IOException ex) {
        }
        
        BufferedWriter out = new BufferedWriter(arquivo);
        //ontologia carregada na máquina de inferencia
        Reasoner reasonerPellet = PelletReasonerFactory.theInstance().create();
        InfModel model1 = ModelFactory.createInfModel(reasonerPellet, ontModel);
        model1.prepare();
        
        //ontModel = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
        
        //modelo.begin();
        //model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
        //utilizar RDF/XML-ABBREV, so RDF/XML da erro no protege!
        model1.write(out, "RDF/XML-ABBREV");
        saida = "\nontologia inferida com sucesso!!!";
        return saida;}*/
    public ReasonerOntologia(){
        if(infModel == null){
            Model model = ModelFactory.createDefaultModel();
            //model.read(ontology);
            Path input = Paths.get(path, "vaquinha_importacao.owl");
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
    
    /**
     * Busca todas as propriedades de um indivíduo, informadas ou inferidas, inclusive Property Chains
     * @param entidade
     * @return 
     */
    public List<String> getPropertiesByIndividualInf(String entidade) {
        String queryStr = "SELECT DISTINCT * \n"
                + "WHERE {<"
                + URI
                + entidade
                + "> ?predicate ?object} ";
        Query query = QueryFactory.create(queryStr);
        QueryExecution execution = QueryExecutionFactory.create(query, infModel);
        ResultSet results = execution.execSelect();
        List<String> list = new ArrayList<>();
        try {
            while (results.hasNext()) {
                QuerySolution qs = results.next();
                Resource predicate = qs.getResource("?predicate");
                String p = predicate.toString();
                if (p.contains("#")) {
                    p = p.split("#")[1];
                }
                String r;
                try {
                    Resource resource = qs.getResource("?object");
                    r = resource.toString();
                } catch (ClassCastException ex) {
                    Literal literal = qs.getLiteral("?object");
                    r = literal.toString();
                }               
                
                if (r.contains("#")) {
                    r = r.split("#")[1];
                }

                if (r != null && !r.equals("null") && predicate.toString() != null && !predicate.toString().equals("null")) {
                    list.add(p + " => " + r);
                }
            }
        } catch (AbstractMethodError ex) {
            ex.printStackTrace();
        }
        execution.close();

        return list;
    }

    /**
     * Busca uma dada propriedades de um indivíduo, informada ou inferida, inclusive Property Chains
     * @param individualName
     * @param objectPropertyName
     * @return 
     */
    public List<String> getOPAssertionsByIndividualInf(String individualName, String objectPropertyName) {
        ObjectProperty objectProperty = ontModel.getObjectProperty(URI + objectPropertyName);
        Resource resource = infModel.getResource(URI + individualName);
        List<String> listProperties = new ArrayList<>();
        if (resource != null && objectProperty != null) {
            for (StmtIterator i = resource.listProperties(objectProperty); i.hasNext();) {
                String str = i.next().getResource().toString();
                listProperties.add(str);
            }
        }
        return listProperties;
    }
    
    public Model getInfModel(){
        if(infModel == null){
            ReasonerOntologia rs = new ReasonerOntologia();
        }
        return infModel;
    }
    
    public String loadReasoner(){
        ReasonerOntologia rs = new ReasonerOntologia();
        
        /*
        System.out.println("Consulta 1 ********************");

        List<String> opAssertionsByIndividualInf = rs.getOPAssertionsByIndividualInf("vaca10591", "vacaTemControleLeiteiro");
        for (String string : opAssertionsByIndividualInf) {
            System.out.println(string);
        }
        System.out.println("Consulta 2 ********************");
        List<String> propertiesByIndividualInf = rs.getPropertiesByIndividualInf("vaca10591");
        for (String string : propertiesByIndividualInf) {
            System.out.println(string);
        }
        System.out.println("FIM ********************");
        */
        //Gerar o novo arquivo com as inferências da ontologia
        //Não está conseguindo escrever o inferido por problema na memória
        //FileWriter arquivo = null;
        //try {
            //caminho para o novo arquivo de ontologia
        //    arquivo = new FileWriter(ontology_inferred);
            //se não tiver o arquivo vai criar, senão será reescrito
        //} catch (IOException ex) {
        //}
        
        //System.out.println("\nChegamos aqui !!!!!!!!!!!!!\n");
        
        //BufferedWriter out = new BufferedWriter(arquivo);
        //infModel.write(out, "RDF/XML-ABBREV");
        
        return "ok";
    }
}
