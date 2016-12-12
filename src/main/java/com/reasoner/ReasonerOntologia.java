package com.reasoner;

import com.thing.Animal;
import com.thing.Consultor;
import com.thing.ControleLeiteiro;
import com.thing.Coordenador;
import com.thing.Fazenda;
import com.thing.Instituicao;
import com.thing.Nucleo;
import com.thing.Producao;
import com.thing.Produtor;
import com.thing.Raca;
import com.thing.Rebanho;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.mindswap.pellet.jena.PelletReasonerFactory;
/*import jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.mindswap.pellet.jena.PelletReasonerFactory;*/
//import org.mindswap.pellet.jena.PelletReasonerFactory;

/**
 *
 * @author Pedro Ivo
 */
public class ReasonerOntologia {
    String path ="C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\";
    //String path = "../../files/ontology/"
    private final String ontologia = path+"vaquinha.owl";    
    private final String newontology = path+"vaquinha_infered.owl";
    
    public String loadOntology() throws SQLException {

        String caracteristicas[];
        String ligacoes[];
        
        //uri da ontologia
        //String baseURI = "http://www.w3.org/ns/prov#";
        String baseURI = "http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#";
                //"C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology";
        
        //inicia a maquina de inferencia e carrega a ontologia passada nela
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        Path input = Paths.get(path, "vaquinha.owl");
        ontModel.read(input.toUri().toString(), "RDF/XML");
        //ontModel.read(ontologia,"RDF/XML");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(ontModel);
        OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM_TRANS_INF;
        ontModelSpec.setReasoner(reasoner);
                
        //Prepara todas as objectsProperties da ontologia
        ObjectProperty armazenaProducao = ontModel.createObjectProperty(baseURI + "armazenaProducao");
        ObjectProperty atende = ontModel.createObjectProperty(baseURI + "atende");
        ObjectProperty consulta = ontModel.createObjectProperty(baseURI + "consulta");
        ObjectProperty coordena = ontModel.createObjectProperty(baseURI + "coordena");
        ObjectProperty ehArmazenado = ontModel.createObjectProperty(baseURI + "ehArmazenado");
        ObjectProperty ehAtendido = ontModel.createObjectProperty(baseURI + "ehAtendido");
        ObjectProperty ehConsultado = ontModel.createObjectProperty(baseURI + "ehConsultado");
        ObjectProperty ehCoordenado = ontModel.createObjectProperty(baseURI + "ehCoordenado");
        ObjectProperty enviaAmostra = ontModel.createObjectProperty(baseURI + "enviaAmostra");
        ObjectProperty hasControleLeiteiro = ontModel.createObjectProperty(baseURI + "hasControleLeiteiro");
        ObjectProperty hasFazenda = ontModel.createObjectProperty(baseURI + "hasFazenda");
        ObjectProperty hasProducao = ontModel.createObjectProperty(baseURI + "hasProducao");
        ObjectProperty hasRaca = ontModel.createObjectProperty(baseURI + "hasRaca");
        ObjectProperty hasRebanho = ontModel.createObjectProperty(baseURI + "hasRebanho");
        ObjectProperty hasVaca = ontModel.createObjectProperty(baseURI + "hasVaca");
        ObjectProperty isControleOf = ontModel.createObjectProperty(baseURI + "isControleOf");
        ObjectProperty isFazendaOf = ontModel.createObjectProperty(baseURI + "isFazendaOf");
        ObjectProperty isProducaoOf = ontModel.createObjectProperty(baseURI + "isProducaoOf");
        ObjectProperty isRacaOf = ontModel.createObjectProperty(baseURI + "isRacaOf");
        ObjectProperty isRebanhoOf = ontModel.createObjectProperty(baseURI + "isRebanhoOf");
        ObjectProperty isVacaOf = ontModel.createObjectProperty(baseURI + "isVacaOf");
        ObjectProperty pertence = ontModel.createObjectProperty(baseURI + "pertence");
        ObjectProperty possui = ontModel.createObjectProperty(baseURI + "possui");
        ObjectProperty recebeAmostra = ontModel.createObjectProperty(baseURI + "recebeAmostra");
        //objetos de property chain
        ObjectProperty coordenadorGerencia = ontModel.createObjectProperty(baseURI + "coordenadorGerencia");
        ObjectProperty ehControleLeiteiroDe = ontModel.createObjectProperty(baseURI + "ehControleLeiteiroDe");
        ObjectProperty ehProprietarioVaca = ontModel.createObjectProperty(baseURI + "ehProprietarioVaca");
        ObjectProperty ehRebanhoProdutor = ontModel.createObjectProperty(baseURI + "ehRebanhoProdutor");
        ObjectProperty instituicaoGerencia = ontModel.createObjectProperty(baseURI + "instituicaoGerencia");
        ObjectProperty nucleoGerencia = ontModel.createObjectProperty(baseURI + "nucleoGerencia");
        ObjectProperty produtorPossuiRebanho = ontModel.createObjectProperty(baseURI + "produtorPossuiRebanho");
        ObjectProperty vacaTemControleLeiteiro = ontModel.createObjectProperty(baseURI + "vacaTemControleLeiteiro");

        String teste = "";
        int cont;
        //Carrega os Entitys na ontologia à partir do banco de dados
        //Animais (Vacas)
        Resource resourceAnimal = ontModel.getResource(baseURI + "Vaca");
        Animal animal = new Animal();
        String animais = animal.buscaAnimais();
        String anims[] = animais.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < anims.length; cont++) {
            caracteristicas = anims[cont].split(";");
            ontModel.createIndividual(baseURI + "vaca" +caracteristicas[0].replace(" ", ""), resourceAnimal);
        }
        teste += "Total Vacas: "+cont+"\n";
        
        //Consultores
        Resource resourceConsultor = ontModel.getResource(baseURI + "Consultor");
        Consultor consultor = new Consultor();
        String consultores = consultor.buscaConsultores();
        String cons[] = consultores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < cons.length; cont++) {
            caracteristicas = cons[cont].split(";");
            ontModel.createIndividual(baseURI + "consultor" +caracteristicas[0].replace(" ", ""), resourceConsultor);
        }        
        teste += "Total Consultores: "+cont+"\n";
    
        //Controles Leiteiros
        ///*
        Resource resourceControleLeiteiro = ontModel.getResource(baseURI + "ControleLeiteiro");
        ControleLeiteiro controle_leiteiro = new ControleLeiteiro();
        String controles = controle_leiteiro.buscaControlesLeiteiros();
        String controls[] = controles.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < controls.length; cont++) {
            caracteristicas = controls[cont].split(";");
            ontModel.createIndividual(baseURI + "controleLeiteiro" +caracteristicas[0].replace(" ", ""), resourceControleLeiteiro);
        }
        teste += "Total Controles: "+cont+"\n";
        //*/
        //Coordenadores
        Resource resourceCoordenador = ontModel.getResource(baseURI + "Coordenador");
        Coordenador coordenador = new Coordenador();
        String coordenadores = coordenador.buscaCoordenadores();
        String coords[] = coordenadores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < coords.length; cont++) {
            caracteristicas = coords[cont].split(";");
            ontModel.createIndividual(baseURI + "coordenador" +caracteristicas[0].replace(" ", ""), resourceCoordenador);
        }
        teste += "Total Coordenadores: "+cont+"\n";
        ///*
        //Fazendas
        Resource resourceFazenda = ontModel.getResource(baseURI + "Fazenda");
        Fazenda fazenda = new Fazenda();
        String fazendas = fazenda.buscaFazendas();
        String fazends[] = fazendas.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < fazends.length; cont++) {
            caracteristicas = fazends[cont].split(";");
            ontModel.createIndividual(baseURI + "fazenda" +caracteristicas[0].replace(" ", ""), resourceFazenda);
        }
        teste += "Total Fazendas: "+cont+"\n";
        
        //Coordenadores
        Resource resourceInstituicao = ontModel.getResource(baseURI + "Instituicao");
        Instituicao instituicao = new Instituicao();
        String instituicoes = instituicao.buscaInstituicoes();
        String instits[] = instituicoes.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < instits.length; cont++) {
            caracteristicas = instits[cont].split(";");
            ontModel.createIndividual(baseURI + "instituicao" +caracteristicas[0].replace(" ", ""), resourceInstituicao);
        }
        teste += "Total Instituicoes: "+cont+"\n";
        
        //Nucleos
        Resource resourceNucleo = ontModel.getResource(baseURI + "Nucleo");
        Nucleo nucleo = new Nucleo();
        String nucleos = nucleo.buscaNucleos();
        String nucs[] = nucleos.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < nucs.length; cont++) {
            caracteristicas = nucs[cont].split(";");
            ontModel.createIndividual(baseURI + "nucleo" +caracteristicas[0].replace(" ", ""), resourceNucleo);
        }
        teste += "Total Núcleos: "+cont+"\n";
        ///*
        //Produções / Lactacoes
        Resource resourceProducao = ontModel.getResource(baseURI + "Producao");
        Producao producao = new Producao();
        String producoes = producao.buscaLactacoes();
        String prods[] = producoes.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < prods.length; cont++) {
            caracteristicas = prods[cont].split(";");
            ontModel.createIndividual(baseURI + "producao" +caracteristicas[0].replace(" ", ""), resourceProducao);
        }
        teste += "Total Produções/Lactação: "+cont+"\n";
        
        //Produtores
        /*Resource resourceProdutor = model.getResource(baseURI + "Produtor");
        Produtor produtor = new Produtor();
        String produtores = produtor.buscaProdutores();
        String produts[] = produtores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < produts.length; cont++) {
            caracteristicas = produts[cont].split(";");
            ontModel.createIndividual(baseURI + "produtor" +caracteristicas[0].replace(" ", ""), resourceProdutor);
        }
        teste += "Total Produtores: "+cont+"\n";
        */
        //Racas
        Resource resourceRaca = ontModel.getResource(baseURI + "RacaVaca");
        Raca raca = new Raca();
        String racas = raca.buscaRacas();
        String racs[] = racas.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < racs.length; cont++) {
            caracteristicas = racs[cont].split(";");
            ontModel.createIndividual(baseURI + "raca" +caracteristicas[0].replace(" ", ""), resourceRaca);
        }
        teste += "Total Raças: "+cont+"\n";
        
        //Rebanhos
        /*Resource resourceRebanho = model.getResource(baseURI + "Rebanho");
        Rebanho rebanho = new Rebanho();
        String rebanhos = rebanho.buscaRebanhos();
        String rebs[] = rebanhos.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < rebs.length; cont++) {
            caracteristicas = rebs[cont].split(";");
            ontModel.createIndividual(baseURI + "rebanho" +caracteristicas[0].replace(" ", ""), resourceRebanho);
        }
        teste += "Total Rebanhos: "+cont+"\n";
        */
        
        //Define as relações do pessoal (object property)========================================
        /*
        String armProd = tanque.buscaProducoes() -> Falta implementar;
        String armazenaproducoes[] = armProd.split("\n");
        for (cont = 1; cont < armazenaproducoes.length; cont++) {
            ligacoes = armazenaproducoes[cont].split(";");
            Individual tanque = ontModel.getIndividual(baseURI + "vaca" +ligacoes[0].replace(" ", ""));
            Individual prod = ontModel.getIndividual(baseURI + "producao" + ligacoes[1].replace(" ",""));
            vaca.addProperty(armazenaProducao, prod);
        }*/
        
        //Propriedade de consulta (Consultor)-[consulta]->(Fazenda)
        String consult = consultor.buscaFazendasConsultadas();
        String consultam[] = consult.split("\n");
        for (cont = 1; cont < consultam.length; cont++) {
            ligacoes = consultam[cont].split(";");
            Individual con = ontModel.getIndividual(baseURI + "consultor" +ligacoes[0].replace(" ", ""));
            Individual faz = ontModel.getIndividual(baseURI + "fazenda" + ligacoes[1].replace(" ",""));
            con.addProperty(consulta, faz);
        }
        teste += "Total Consultores -> Fazendas: "+cont+"\n";
        
        //Propriedade de coordena (Coordenador)-[coordena]->(Consultor)
        String coord = coordenador.buscaConsultoresCoordenados();
        String coordenam[] = coord.split("\n");
        for (cont = 1; cont < coordenam.length; cont++) {
            ligacoes = coordenam[cont].split(";");
            Individual coord1 = ontModel.getIndividual(baseURI + "coordenador" +ligacoes[0].replace(" ", ""));
            Individual con1 = ontModel.getIndividual(baseURI + "consultor" + ligacoes[1].replace(" ",""));
            coord1.addProperty(coordena, con1);
        }
        teste += "Total Coordenadores -> Consultores: "+cont+"\n";
        
        //Propriedade de hasControleLeiteiro (Producao)-[hasControleLeiteiro]->(ControleLeiteiro)
        String ctrl = producao.buscaControlesLeiteiros();
        String ctrls[] = ctrl.split("\n");
        for (cont = 1; cont < ctrls.length; cont++) {
            ligacoes = ctrls[cont].split(";");
            Individual lact1 = ontModel.getIndividual(baseURI + "producao" +ligacoes[0].replace(" ", ""));
            Individual ctrl1 = ontModel.getIndividual(baseURI + "controleLeiteiro" + ligacoes[1].replace(" ",""));
            lact1.addProperty(hasControleLeiteiro, ctrl1);
        }
        teste += "Total Produção -> Controle Leiteiro: "+cont+"\n";

        //Propriedade de produção (Vaca)-[hasProducao]->(produção)
        String hasprods = animal.buscaProducoes();
        String hasproducoes[] = hasprods.split("\n");
        for (cont = 1; cont < hasproducoes.length; cont++) {
            ligacoes = hasproducoes[cont].split(";");
            Individual vaca = ontModel.getIndividual(baseURI + "vaca" +ligacoes[0].replace(" ", ""));
            Individual prod = ontModel.getIndividual(baseURI + "producao" + ligacoes[1].replace(" ",""));
            vaca.addProperty(hasProducao, prod);
        }        
        teste += "Total Animais -> Produção: "+cont+"\n";

        //Propriedade de hasRaca (Vaca)-[hasRaca]->(Raca)
        String vaca = animal.buscaRacas();
        String vacas[] = vaca.split("\n");
        for (cont = 1; cont < vacas.length; cont++) {
            ligacoes = vacas[cont].split(";");
            Individual vaca1 = ontModel.getIndividual(baseURI + "vaca" +ligacoes[0].replace(" ", ""));
            Individual raca1 = ontModel.getIndividual(baseURI + "raca" + ligacoes[1].replace(" ",""));
            vaca1.addProperty(hasRaca, raca1);
        }
        teste += "Total Animais -> Raça: "+cont+"\n";

        //Propriedade de possui (Núcleo)-[possui]->(Coordenador)
        String nucle = nucleo.buscaCoordenadores();
        String nucleos1[] = nucle.split("\n");
        for (cont = 1; cont < nucleos1.length; cont++) {
            ligacoes = nucleos1[cont].split(";");
            Individual nucleo1 = ontModel.getIndividual(baseURI + "nucleo" +ligacoes[0].replace(" ", ""));
            Individual coord1 = ontModel.getIndividual(baseURI + "coordenador" + ligacoes[1].replace(" ",""));
            nucleo1.addProperty(possui, coord1);
        }
        teste += "Total Núcleo -> Coordenador: "+cont+"\n";

        //Criar todas as propriedades aqui
        
        //validar a nova ontologia a ser criada
        //InfModel modelInf = ModelFactory.createInfModel(reasoner, ontModel);
        //reasoner = reasoner.bindSchema(ontModel); 
        //Gerar o novo arquivo com os dados do banco na nova ontologia
        FileWriter arquivo = null;
        try {
            //caminho para o novo arquivo de ontologia
            arquivo = new FileWriter(newontology);
            //se não tiver o arquivo vai criar, senão será reescrito
        } catch (IOException ex) {
        }
        
        BufferedWriter out = new BufferedWriter(arquivo);
        //BufferedWriter out2 = new BufferedWriter(arquivo2);
        //ontologia carregada na máquina de inferencia
        Reasoner reasonerPellet = PelletReasonerFactory.theInstance().create();
        InfModel model1 = ModelFactory.createInfModel(reasonerPellet, ontModel);
        //model1.prepare();
        
        //ontModel = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
        //InfModel modelo = ModelFactory.createInfModel(reasoner, ontModel);
        //modelo.prepare();
        //modelo.begin();
        //model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
        //utilizar RDF/XML-ABBREV, so RDF/XML da erro no protege!
        //ontModel.write(out, "RDF/XML");
        model1.write(out, "RDF/XML-ABBREV");
teste +=  "cabou o role";
        return teste;
    }

              
        /*
        public List<String> buscartodos() {
        //variavel global
        OntModel model;
        //uri da ontologia
        String baseURI = "http://www.w3.org/ns/prov#";
        //caminho fisico da ontologia
        String ontologia = newontology;

        //inicia a maquina de inferencia e carrega a ontologia nela
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read(ontologia);
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(ontModel);
        OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
        ontModelSpec.setReasoner(reasoner);
        //ontologia carregada na mÃ¡quina de inferencia
        model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);

        List ontologys = new ArrayList();
        OntClass equipe = model.getOntClass(baseURI + "Workflow");
        OntProperty nome = model.getOntProperty(baseURI + "Used");
        String temp1;
        String temp2;
        for (ExtendedIterator<? extends OntResource> instances = equipe.listInstances(); instances.hasNext();) {
            OntResource equipeInstance = instances.next();
            ontologys.add(equipeInstance.getProperty(nome).toString().replace(baseURI, ""));
            //System.out.println("Equipe instance: " + equipeInstance.getProperty(nome).toString().replace("http://www.w3.org/ns/prov#", ""));

            // find out the resources that link to the instance
            for (StmtIterator stmts = model.listStatements(null, null, equipeInstance); stmts.hasNext();) {
                Individual ind = stmts.next().getSubject().as(Individual.class);
                // show the properties of this individual
                //System.out.println("  " + ind.getURI().toString().replace("http://www.w3.org/ns/prov#", ""));
                ontologys.add(ind.getURI().toString().replace(baseURI, ""));

                for (StmtIterator j = ind.listProperties(); j.hasNext();) {
                    Statement s = j.next();
                    //System.out.print("    " + s.getPredicate().getLocalName().toString().replace("http://www.w3.org/ns/prov#", "") + " -> ");
                    temp1 = (s.getPredicate().getLocalName().toString().replace(baseURI, "") + " -> ");
                    if (s.getObject().isLiteral()) {
                        //System.out.println(s.getLiteral().getLexicalForm().replace("http://www.w3.org/ns/prov#", ""));
                        ontologys.add(s.getLiteral().getLexicalForm().replace(baseURI, ""));
                    } else {
                        //System.out.println(s.getObject().toString().replace("http://www.w3.org/ns/prov#", ""));
                        temp2 = s.getObject().toString().replace(baseURI, "");
                        ontologys.add(temp1 + temp2);
                    }
                }
            }
        }
        return ontologys;
    }

    public List<String> buscarSPARQL(String sql) {
        //variavel global
        OntModel model;
        //uri da ontologia
        String baseURI = "http://www.w3.org/ns/prov#";
        //caminho fisico da ontologia
        String ontologia = newontology;

        //inicia a maquina de inferencia e carrega a ontologia nela
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read(ontologia);
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(ontModel);
        OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
        ontModelSpec.setReasoner(reasoner);
        //ontologia carregada na mÃ¡quina de inferencia
        model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);

        if (sql.equals("")) {
            sql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                    + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                    + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                    + "PREFIX prov: <http://www.w3.org/ns/prov#>\n"
                    + "\n"
                    + "SELECT ?subject ?object\n"
                    + "	WHERE { ?subject prov:Used <http://www.w3.org/ns/prov#Sum>}";
        }
        Query query = QueryFactory.create(sql);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        List resultslist = new ArrayList();
        while (results.hasNext()) {
            QuerySolution next = results.next();
            String result = null;
            result = next.toString().replace("( ?subject = <http://www.w3.org/ns/prov#", "");
            resultslist.add(result.replace("> )", ""));
        }

        return resultslist;
    }
 
    }*/
}
