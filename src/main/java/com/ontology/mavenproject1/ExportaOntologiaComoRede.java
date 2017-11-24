package com.ontology.mavenproject1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.hp.hpl.jena.iri.impl.Main;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.PathParam;

/**
 *
 * @author Pedro Ivo
 */
@Path("/exportaOntologia/{type}")
public class ExportaOntologiaComoRede {

    //Variaveis
    private String graph, node, edge;
    private Map<String,Integer> classMaping;

    public ExportaOntologiaComoRede() {
        this.classMaping = new HashMap<>();
        this.graph = "var s,\n" +
        "    g = {nodes: [],edges: []};\n";
        this.edge = "\ng.edges.push(\n";
        this.node = "g.nodes.push(\n";
    }

/**
* Retorna os dados da ontologia na forma de uma rede (estrutura ou indivíduos)
* @param type : Tipo da rede formada (1-estrutural; 2-indivíduos)
* @return texto do grafo que representa a ontologia
*/    
@GET
@Produces({MediaType.TEXT_PLAIN})
public String ontologia(@PathParam("type") String type) {
    
    //Carregando arquivo .owl
    FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
    
    String path ="C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\";
    
    //inicia a maquina de inferencia e carrega a ontologia passada nela
    OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    //java.nio.file.Path input = Paths.get(path, "vaquinhaPlusPlus.owl");
    java.nio.file.Path input = Paths.get(path, "vaquinha_importacao.owl");
    ontModel.read(input.toUri().toString(), "RDF/XML");
//    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
//    reasoner = reasoner.bindSchema(ontModel);
//    OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM_TRANS_INF;
//    ontModelSpec.setReasoner(reasoner);

    //Type 1 para estrutura da ontologia e 2 para indivíduos
    switch(type){
        case "1": 
            getClasses(ontModel);
            getSubclasses(ontModel);
            getObjectProperties(ontModel);
            break;
        case "2":
            getIndividuals(ontModel);
            getRelations(ontModel);
            break;
        default:
            this.node = ");";
            this.edge = ");";
            break;
    }

    this.graph += this.node + this.edge;
    
    return this.graph;
}

/**
* Busca todos as classes da ontologia e suas ligações de subclasses
* @param model
*/
public void getClasses(OntModel model) {
    
    ExtendedIterator classes = model.listClasses();
    int id = 0;
    
    while (classes.hasNext())
    {
        OntClass thisClass = (OntClass) classes.next();
        
        //Criando o nó caso não for uma classe lixo
        if(thisClass.getLocalName() != null){
            id++;
            classMaping.put(thisClass.getLocalName(), id);
            
            this.node += "{\n"+
            "      id: '"+id+"',\n" +
            "      label: '"+thisClass.getLocalName()+"',\n" +
            "      x: Math.random(),\n" +
            "      y: Math.random(),\n" +
            "      size: 1,\n" +
            "      color: '#0000ff',\n" +
            "      data: {\n" +
            "        name: '"+thisClass.getLocalName()+"',\n" +
            "      }\n" +
            "},\n"    
            ;
        }
    }
    
    if(this.node.endsWith(",\n")){
        this.node = this.node.substring(0, this.node.length()-2) + ");\n";
    }    
}

/**
* Busca todos as classes da ontologia e suas ligações de subclasses
* @param model
*/
public void getIndividuals(OntModel model) {
    
    ExtendedIterator classes = model.listClasses();
    int id = 0;
    
    while (classes.hasNext())
    {
        OntClass thisClass = (OntClass) classes.next();
        
        //Criando o nó caso não for uma classe lixo
        if(thisClass.getLocalName() != null){
            
            ExtendedIterator individuals = thisClass.listInstances();
            
            Random randCol = new Random();
            String color = String.format("#%06X", randCol.nextInt(0xFFFFFF+1)); 
            
            while(individuals.hasNext()){
                Individual individual = (Individual)individuals.next();
                
                id++;
                classMaping.put(individual.getLocalName(), id);
                
                //No campo data posso fazer um laço para pegar todas data properties
                this.node += "{\n"+
                "      id: '"+id+"',\n" +
                "      label: '"+individual.getLocalName()+"',\n" +
                "      x: Math.random(),\n" +
                "      y: Math.random(),\n" +
                "      size: 1,\n" +
                "      color: '"+color+"',\n" +
                "      data: {\n" +
                "        name: '"+individual.getLocalName()+"',\n" +
                "      }\n" +
                "},\n"    
                ;
            }
        }
    }
    
    if(this.node.endsWith(",\n")){
        this.node = this.node.substring(0, this.node.length()-2) + ");\n";
    }    
}

/**
* Busca todos as ligações de subclasses da ontologia
* @param model
*/
public void getSubclasses(OntModel model) {
    
    ExtendedIterator classes = model.listClasses();
    int idEdge = 0;
    
    while (classes.hasNext())
    {
        OntClass thisClass = (OntClass) classes.next();
        
        //caso não for uma classe lixo
        if(thisClass.getLocalName() != null){
            
            ExtendedIterator subClasses = thisClass.listSubClasses();
            while (subClasses.hasNext()){
                OntClass subclass = (OntClass) subClasses.next();
                
                if(subclass.getLocalName() != null && this.classMaping.containsKey(thisClass.getLocalName())
                        && this.classMaping.containsKey(subclass.getLocalName()) ){
                    int id = this.classMaping.get(thisClass.getLocalName());
                    int idSub = this.classMaping.get(subclass.getLocalName());
                    idEdge++;
                    
                    //Criando a ligação de subclasses
                    this.edge += "{\n" +
                    "      id: 'e"+idEdge+"',\n" +
                    "      label: 'subClassOf',\n" +
                    "      source: '"+id+"',\n" +
                    "      target: '"+idSub+"',\n" +
                    "      color: '#0000ff',\n" +
                    "      hover_color: '#FC0',\n" +
                    "      type: 'arrow',\n" +           
                    "      size: 1      \n" +
                    "    },\n"
                    ;
                }
            }
        }
    }    
}

/**
* Busca todas as propriedades de objeto da ontologia
* @param model
*/
public void getObjectProperties(OntModel model) {
    
    ExtendedIterator properties = model.listObjectProperties();
    int idEdge = 0;
    
    //Vai passando por todas as propriedades da ontologia
    while (properties.hasNext())
    {
        ObjectProperty thisProperty = (ObjectProperty) properties.next();
        
        //caso não for uma propriedade lixo e que tenha range e domain
        if(thisProperty.getLocalName() != null && thisProperty.listDomain() != null &&
                thisProperty.listRange() != null){
            
            //Listar todas os domains e ranges (caso exista mais de um)
            ArrayList<String> domain = new ArrayList<>();
            ArrayList<String> range = new ArrayList<>();
// Usar model.listUnionClasses() ou algo assim para ver as property com mais de uma...
            //Talvez eu precise colocar uma exceção pra quando o range ou domain for um valor (tipo date?)
            ExtendedIterator domains = thisProperty.listDomain();
            while(domains.hasNext()){
                OntClass dom = (OntClass) domains.next();
                if(dom != null && dom.getLocalName() != null)
                    domain.add(dom.getLocalName());
            }
            
            ExtendedIterator ranges = thisProperty.listRange();
            while (ranges.hasNext()){
                OntClass ran = (OntClass) ranges.next();
                if(ran != null && ran.getLocalName() != null)
                    //System.out.println(ran.getURI());
                    range.add(ran.getLocalName());
            }
            
            for(String d : domain){
                for(String r : range){
                    int idDomain = this.classMaping.get( d );
                    int idRange = this.classMaping.get( r );
                    
                    idEdge++;
                    //Criando a ligação de subclasses
                    this.edge += "{\n" +
                    "      id: 'ed"+idEdge+"',\n" +
                    "      label: '"+thisProperty.getLocalName()+"',\n" +
                    "      source: '"+idDomain+"',\n" +
                    "      target: '"+idRange+"',\n" +
                    "      color: '#000000',\n" +
                    "      hover_color: '#FC0',\n" +
                    "      type: 'arrow',\n" +
                    "      size: 1      \n" +
                    "    },\n"
                    ;
                }
            } 
        }
    }        

    if(this.edge.endsWith(",\n"))
        this.edge = this.edge.substring(0, this.edge.length()-2) + ");\n";
}

/**
* Busca todas as propriedades de objeto da ontologia
* @param model
*/
public void getRelations(OntModel model) {
    
    int idEdge = 0;
    
    //Gambiarra:
    ExtendedIterator properties = model.listObjectProperties();
    int i = 0;
    Map<String,Integer> objectMaping = new HashMap<>();
    while(properties.hasNext()){
        ObjectProperty obj = (ObjectProperty) properties.next();
        objectMaping.put(obj.getLocalName(), i++);
    }
    
    //Pegando todas as triplas da ontologia
    StmtIterator stm = model.listStatements();
    
    while(stm.hasNext()){
        Statement s = stm.next();
        
//      the subject is the resource from which the arc leaves
//      the predicate is the property that labels the arc
//      the object is the resource or literal pointed to by the arc
        
        //Se for uma object property eu adiciono a aresta
        if(objectMaping.containsKey(s.getPredicate().getLocalName())){
      
            String d = s.getSubject().getLocalName();
            String r = s.getObject().toString().substring(s.getObject().toString().lastIndexOf("#")+1);
            
            int idDomain = this.classMaping.get( d );
            int idRange = this.classMaping.get( r );

            //Criando a ligação de subclasses
            this.edge += "{\n" +
            "      id: 'ed"+(idEdge++)+"',\n" +
            "      label: '"+s.getPredicate().getLocalName()+"',\n" +
            "      source: '"+idDomain+"',\n" +
            "      target: '"+idRange+"',\n" +
            "      color: '#000000',\n" +
            "      hover_color: '#FC0',\n" +
            "      type: 'arrow',\n" +
            "      size: 1      \n" +
            "    },\n"
            ;
        }
    }

    if(this.edge.endsWith(",\n"))
        this.edge = this.edge.substring(0, this.edge.length()-2) + ");\n";
}

//Customizar os nodes e os edges
//https://github.com/Linkurious/linkurious.js/wiki/Styling
}
