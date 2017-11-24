package com.reasoner;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
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
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Pedro Ivo
 */
public class ImportaDadosParaOntologia {
    String path ="C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology\\";
      
    private final String newontology = path+"vaquinha_importacao.owl";
    
    public String loadSQL() throws SQLException {

        String caracteristicas[];
        String ligacoes[];
       
        //uri da ontologia
        String baseURI = "http://www.semanticweb.org/pedroivo/ontologies/2016/6/vaquinha.owl#";
                //"C:\\Users\\Pedro Ivo\\Documents\\NetBeansProjects\\visualizacao_semantica_estrutural\\files\\ontology";
        
        //inicia a maquina de inferencia e carrega a ontologia passada nela
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        Path input = Paths.get(path, "vaquinhaPlusPlus.owl");
        ontModel.read(input.toUri().toString(), "RDF/XML");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(ontModel);
        OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM_TRANS_INF;
        ontModelSpec.setReasoner(reasoner);
                
        //Prepara todas as objectsProperties da ontologia
        ObjectProperty consulta = ontModel.createObjectProperty(baseURI + "consultsFarm");
        ObjectProperty coordena = ontModel.createObjectProperty(baseURI + "coordinatesConsultant");
        ObjectProperty hasControleLeiteiro = ontModel.createObjectProperty(baseURI + "hasDairyControl");
        ObjectProperty hasProducao = ontModel.createObjectProperty(baseURI + "hasProduction");
        ObjectProperty hasRebanho = ontModel.createObjectProperty(baseURI + "hasHerd");
        ObjectProperty hasVaca = ontModel.createObjectProperty(baseURI + "hasAnimal");
        ObjectProperty possui = ontModel.createObjectProperty(baseURI + "hasCoordinator");
        //objetos de property chain
        //ObjectProperty coordenadorGerencia = ontModel.createObjectProperty(baseURI + "coordenadorGerencia");
       
        //Data property utilizados na ontologia
        DatatypeProperty DataControleLeiteiro = ontModel.createDatatypeProperty(baseURI + "DataControleLeiteiro");
        DataControleLeiteiro.setRange(ResourceFactory.createResource(XSDDatatype.XSDdate.getURI()));
        DatatypeProperty DataFimLactacao = ontModel.createDatatypeProperty(baseURI + "DataFimLactacao");
        DataFimLactacao.setRange(ResourceFactory.createResource(XSDDatatype.XSDdate.getURI()));
        DatatypeProperty DataInicioLactacao = ontModel.createDatatypeProperty(baseURI + "DataInicioLactacao");
        DataInicioLactacao.setRange(ResourceFactory.createResource(XSDDatatype.XSDdate.getURI()));
        
        DatatypeProperty NomeConsultor = ontModel.createDatatypeProperty(baseURI + "NomeConsultor");
        DatatypeProperty NomeCoordenador = ontModel.createDatatypeProperty(baseURI + "NomeCoordenador");
        DatatypeProperty NomeNucleo = ontModel.createDatatypeProperty(baseURI + "NomeNucleo");
        DatatypeProperty NomeInstituicao = ontModel.createDatatypeProperty(baseURI + "NomeInstituicao");
        DatatypeProperty NomeFazenda = ontModel.createDatatypeProperty(baseURI + "NomeFazenda");
        DatatypeProperty NomeProdutor = ontModel.createDatatypeProperty(baseURI + "NomeProdutor");
        DatatypeProperty NomeRebanho = ontModel.createDatatypeProperty(baseURI + "NomeRebanho");
        DatatypeProperty NomeVaca = ontModel.createDatatypeProperty(baseURI + "NomeVaca");
        DatatypeProperty NomeRaca = ontModel.createDatatypeProperty(baseURI + "NomeRaca");
        
        DatatypeProperty ValorAcumuladoLeite = ontModel.createDatatypeProperty(baseURI + "ValorAcumuladoLeite");
        ValorAcumuladoLeite.setRange(ResourceFactory.createResource(XSDDatatype.XSDfloat.getURI()));
        DatatypeProperty ValorCCSAnteriorLeite = ontModel.createDatatypeProperty(baseURI + "ValorCCSAnteriorLeite");
        ValorCCSAnteriorLeite.setRange(ResourceFactory.createResource(XSDDatatype.XSDinteger.getURI()));
        DatatypeProperty ValorCCSLeite = ontModel.createDatatypeProperty(baseURI + "ValorCCSLeite");
        ValorCCSLeite.setRange(ResourceFactory.createResource(XSDDatatype.XSDinteger.getURI()));
        DatatypeProperty ValorGorduraLeite = ontModel.createDatatypeProperty(baseURI + "ValorGorduraLeite");
        ValorGorduraLeite.setRange(ResourceFactory.createResource(XSDDatatype.XSDfloat.getURI()));
        DatatypeProperty ValorProteinaLeite = ontModel.createDatatypeProperty(baseURI + "ValorProteinaLeite");
        ValorProteinaLeite.setRange(ResourceFactory.createResource(XSDDatatype.XSDfloat.getURI()));
        

        String teste = "";
        int cont;
        //Carrega os Entitys na ontologia à partir do banco de dados
        //Animais (Vacas)
        Resource resourceAnimal = ontModel.getResource(baseURI + "Cow");
        Animal animal = new Animal();
        String animais = animal.buscaAnimais();
        String anims[] = animais.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < anims.length; cont++) {
            caracteristicas = anims[cont].split(";");
            ontModel.createIndividual(baseURI + "cow" +caracteristicas[0].replace(" ", ""), resourceAnimal);
            Individual ind = ontModel.getIndividual(baseURI + "cow" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeVaca, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeVaca, "cow"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Vacas: "+cont+"\n";
        
        //Consultores
        Resource resourceConsultor = ontModel.getResource(baseURI + "Consultant");
        Consultor consultor = new Consultor();
        String consultores = consultor.buscaConsultores();
        String cons[] = consultores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < cons.length; cont++) {
            caracteristicas = cons[cont].split(";");
            ontModel.createIndividual(baseURI + "consultant" +caracteristicas[0].replace(" ", ""), resourceConsultor);
            Individual ind = ontModel.getIndividual(baseURI + "consultant" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeConsultor, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeConsultor, "consultant"+caracteristicas[0].replace(" ", ""));
        }        
        teste += "Total Consultores: "+cont+"\n";
    
        //Controles Leiteiros
        ///*
        Resource resourceControleLeiteiro = ontModel.getResource(baseURI + "DairyControl");
        ControleLeiteiro controle_leiteiro = new ControleLeiteiro();
        String controles = controle_leiteiro.buscaControlesLeiteiros();
        String controls[] = controles.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < controls.length; cont++) {
            caracteristicas = controls[cont].split(";");
            ontModel.createIndividual(baseURI + "dairyControl" +caracteristicas[0].replace(" ", ""), resourceControleLeiteiro);
            Individual ind = ontModel.getIndividual(baseURI + "dairyControl" +caracteristicas[0].replace(" ", ""));
            if(!caracteristicas[1].replace(" ", "").isEmpty() && !caracteristicas[1].replace(" ", "").equals("null")){
                ind.addProperty(DataControleLeiteiro, caracteristicas[1].replace(" ", ""),XSDDatatype.XSDdate);
            }
            if(!caracteristicas[2].replace(" ", "").isEmpty() && !caracteristicas[2].replace(" ", "").equals("null")){
                ind.addProperty(ValorAcumuladoLeite, caracteristicas[2].replace(" ", ""),XSDDatatype.XSDfloat);
            }
            if(!caracteristicas[3].replace(" ", "").isEmpty() && !caracteristicas[3].replace(" ", "").equals("null")){
                ind.addProperty(ValorCCSLeite, caracteristicas[3].replace(" ", ""),XSDDatatype.XSDinteger);
            }
            if(!caracteristicas[4].replace(" ", "").isEmpty() && !caracteristicas[4].replace(" ", "").equals("null")){
                ind.addProperty(ValorCCSAnteriorLeite, caracteristicas[4].replace(" ", ""),XSDDatatype.XSDinteger);
            }
            if(!caracteristicas[5].replace(" ", "").isEmpty() && !caracteristicas[5].replace(" ", "").equals("null")){
                ind.addProperty(ValorGorduraLeite, caracteristicas[5].replace(" ", ""),XSDDatatype.XSDfloat);
            }
            if(!caracteristicas[6].replace(" ", "").isEmpty() && !caracteristicas[6].replace(" ", "").equals("null")){
                ind.addProperty(ValorProteinaLeite, caracteristicas[6].replace(" ", ""),XSDDatatype.XSDfloat);
            }
        }
        teste += "Total Controles: "+cont+"\n";
        //*/
        //Coordenadores
        Resource resourceCoordenador = ontModel.getResource(baseURI + "Coordinator");
        Coordenador coordenador = new Coordenador();
        String coordenadores = coordenador.buscaCoordenadores();
        String coords[] = coordenadores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < coords.length; cont++) {
            caracteristicas = coords[cont].split(";");
            ontModel.createIndividual(baseURI + "coordinator" +caracteristicas[0].replace(" ", ""), resourceCoordenador);
            Individual ind = ontModel.getIndividual(baseURI + "coordinator" +caracteristicas[0].replace(" ", ""));
            ind.addProperty(NomeCoordenador, "coordinator"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Coordenadores: "+cont+"\n";
        
        //Fazendas
        Resource resourceFazenda = ontModel.getResource(baseURI + "Farm");
        Fazenda fazenda = new Fazenda();
        String fazendas = fazenda.buscaFazendas();
        String fazends[] = fazendas.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < fazends.length; cont++) {
            caracteristicas = fazends[cont].split(";");
            ontModel.createIndividual(baseURI + "farm" +caracteristicas[0].replace(" ", ""), resourceFazenda);
            Individual ind = ontModel.getIndividual(baseURI + "farm" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeFazenda, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeFazenda, "farm"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Fazendas: "+cont+"\n";
        
        //Instituicoes
        Resource resourceInstituicao = ontModel.getResource(baseURI + "Institution");
        Instituicao instituicao = new Instituicao();
        String instituicoes = instituicao.buscaInstituicoes();
        String instits[] = instituicoes.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < instits.length; cont++) {
            caracteristicas = instits[cont].split(";");
            ontModel.createIndividual(baseURI + "institution" +caracteristicas[0].replace(" ", ""), resourceInstituicao);
            Individual ind = ontModel.getIndividual(baseURI + "institution" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeInstituicao, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeInstituicao, "institution"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Instituicoes: "+cont+"\n";
        
        //Nucleos
        Resource resourceNucleo = ontModel.getResource(baseURI + "RegionalCenter");
        Nucleo nucleo = new Nucleo();
        String nucleos = nucleo.buscaNucleos();
        String nucs[] = nucleos.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < nucs.length; cont++) {
            caracteristicas = nucs[cont].split(";");
            ontModel.createIndividual(baseURI + "regionalCenter" +caracteristicas[0].replace(" ", ""), resourceNucleo);
            Individual ind = ontModel.getIndividual(baseURI + "regionalCenter" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeNucleo, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeNucleo, "regionalCenter"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Nucleos: "+cont+"\n";
        ///*
        //Produções / Lactacoes
        Resource resourceProducao = ontModel.getResource(baseURI + "Production");
        Producao producao = new Producao();
        String producoes = producao.buscaLactacoes();
        String prods[] = producoes.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < prods.length; cont++) {
            caracteristicas = prods[cont].split(";");
            ontModel.createIndividual(baseURI + "production" +caracteristicas[0].replace(" ", ""), resourceProducao);
            Individual ind = ontModel.getIndividual(baseURI + "production" +caracteristicas[0].replace(" ", ""));
            if(!caracteristicas[1].replace(" ", "").isEmpty() && !caracteristicas[1].replace(" ", "").equals("null")){
                ind.addProperty(DataInicioLactacao, caracteristicas[1].replace(" ", ""),XSDDatatype.XSDdate);
            }
            if(!caracteristicas[2].replace(" ", "").isEmpty() && !caracteristicas[2].replace(" ", "").equals("null")){
                ind.addProperty(DataFimLactacao, caracteristicas[2].replace(" ", ""),XSDDatatype.XSDdate);
            }
        }
        teste += "Total Producoes/Lactacao: "+cont+"\n";
        
        //Produtores
        Resource resourceProdutor = ontModel.getResource(baseURI + "Producer");
        Produtor produtor = new Produtor();
        String produtores = produtor.buscaProdutores();
        String produts[] = produtores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < produts.length; cont++) {
            caracteristicas = produts[cont].split(";");
            ontModel.createIndividual(baseURI + "producer" +caracteristicas[0].replace(" ", ""), resourceProdutor);
            Individual ind = ontModel.getIndividual(baseURI + "producer" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeProdutor, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeProdutor, "producer"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Produtores: "+cont+"\n";
        
        //Racas
        Resource resourceRaca = ontModel.getResource(baseURI + "AnimalBreed");
        Raca raca = new Raca();
        String racas = raca.buscaRacas();
        String racs[] = racas.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < racs.length; cont++) {
            caracteristicas = racs[cont].split(";");
            ontModel.createIndividual(baseURI + "animalBreed" +caracteristicas[0].replace(" ", ""), resourceRaca);
            //Individual ind = ontModel.getIndividual(baseURI + "raca" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeRaca, caracteristicas[1].replace("  ", ""));
            //ind.addProperty(NomeRaca, "raca"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Racas: "+cont+"\n";
        
        //Rebanhos
        Resource resourceRebanho = ontModel.getResource(baseURI + "Herd");
        Rebanho rebanho = new Rebanho();
        String rebanhos = rebanho.buscaRebanhos();
        String rebs[] = rebanhos.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < rebs.length; cont++) {
            caracteristicas = rebs[cont].split(";");
            ontModel.createIndividual(baseURI + "herd" +caracteristicas[0].replace(" ", ""), resourceRebanho);
            Individual ind = ontModel.getIndividual(baseURI + "herd" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeRebanho, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeRebanho, "herd"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Rebanhos: "+cont+"\n";
        
        
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
            Individual con = ontModel.getIndividual(baseURI + "consultant" +ligacoes[0].replace(" ", ""));
            Individual faz = ontModel.getIndividual(baseURI + "farm" + ligacoes[1].replace(" ",""));
            if(con != null && faz != null) con.addProperty(consulta, faz);
        }
        teste += "Total Consultores -> Fazendas: "+cont+"\n";
        
        //Propriedade de coordena (Coordenador)-[coordena]->(Consultor)
        String coord = coordenador.buscaConsultoresCoordenados();
        String coordenam[] = coord.split("\n");
        for (cont = 1; cont < coordenam.length; cont++) {
            ligacoes = coordenam[cont].split(";");
            Individual coord1 = ontModel.getIndividual(baseURI + "coordinator" +ligacoes[0].replace(" ", ""));
            Individual con1 = ontModel.getIndividual(baseURI + "consultant" + ligacoes[1].replace(" ",""));
            if(coord1 != null && con1 != null) coord1.addProperty(coordena, con1);
        }
        teste += "Total Coordenadores -> Consultores: "+cont+"\n";
        
        //Propriedade de hasControleLeiteiro (Producao)-[hasControleLeiteiro]->(ControleLeiteiro)
        String ctrl = producao.buscaControlesLeiteiros();
        String ctrls[] = ctrl.split("\n");
        for (cont = 1; cont < ctrls.length; cont++) {
            ligacoes = ctrls[cont].split(";");
            Individual lact1 = ontModel.getIndividual(baseURI + "production" +ligacoes[1].replace(" ", ""));
            Individual ctrl1 = ontModel.getIndividual(baseURI + "dairyControl" + ligacoes[2].replace(" ",""));
            if(lact1 != null && ctrl1 != null) lact1.addProperty(hasControleLeiteiro, ctrl1);
        }
        teste += "Total Producao -> Controle Leiteiro: "+cont+"\n";

        //Propriedade de produção (Vaca)-[hasProducao]->(produção)
        String hasprods = producao.buscaControlesLeiteiros();
        String hasproducoes[] = hasprods.split("\n");
        for (cont = 1; cont < hasproducoes.length; cont++) {
            ligacoes = hasproducoes[cont].split(";");
            Individual vaca = ontModel.getIndividual(baseURI + "cow" +ligacoes[0].replace(" ", ""));
            Individual prod = ontModel.getIndividual(baseURI + "production" + ligacoes[1].replace(" ",""));
            if(vaca != null && prod != null) vaca.addProperty(hasProducao, prod);
        }        
        teste += "Total Animais -> Producao: "+cont+"\n";
        
        //Propriedade de produção (Rebanho)-[hasVaca]->(vaca)
        String hasanims = rebanho.buscaAnimaisRelacionados();
        String hasanimais[] = hasanims.split("\n");
        for (cont = 1; cont < hasanimais.length; cont++) {
            ligacoes = hasanimais[cont].split(";");
            Individual reb = ontModel.getIndividual(baseURI + "herd" +ligacoes[0].replace(" ", ""));
            Individual vaca = ontModel.getIndividual(baseURI + "cow" + ligacoes[1].replace(" ",""));
            if(vaca != null && reb != null) reb.addProperty(hasVaca, vaca);
        }        
        teste += "Total Rebanhos -> Animais: "+cont+"\n";

        //Rebanhos de uma fazeda (Fazenda)-[hasRebanho]->(rebanho)
        String hasrebs = fazenda.buscaRebanhosRelacionados();
        String hasrebanhos[] = hasrebs.split("\n");
        for (cont = 1; cont < hasrebanhos.length; cont++) {
            ligacoes = hasrebanhos[cont].split(";");
            Individual reb = ontModel.getIndividual(baseURI + "herd" +ligacoes[1].replace(" ", ""));
            Individual faz = ontModel.getIndividual(baseURI + "farm" + ligacoes[0].replace(" ",""));
            if(faz != null && reb != null) faz.addProperty(hasRebanho, reb);
        }        
        teste += "Total Fazendas -> Rebanhos: "+cont+"\n";
        
        /*
        //Propriedade de hasRaca (Vaca)-[hasRaca]->(Raca)
        String vaca = animal.buscaRacas();
        String vacas[] = vaca.split("\n");
        for (cont = 1; cont < vacas.length; cont++) {
            ligacoes = vacas[cont].split(";");
            Individual vaca1 = ontModel.getIndividual(baseURI + "vaca" +ligacoes[0].replace(" ", ""));
            Individual raca1 = ontModel.getIndividual(baseURI + "raca" + ligacoes[1].replace(" ",""));
            if(vaca1 != null && raca1 != null) vaca1.addProperty(hasRaca, raca1);
        }
        teste += "Total Animais -> Raça: "+cont+"\n";
*/
        //Propriedade de possui (Núcleo)-[possui]->(Coordenador)
        String nucle = nucleo.buscaCoordenadores();
        String nucleos1[] = nucle.split("\n");
        for (cont = 1; cont < nucleos1.length; cont++) {
            ligacoes = nucleos1[cont].split(";");
            Individual nucleo1 = ontModel.getIndividual(baseURI + "regionalCenter" +ligacoes[0].replace(" ", ""));
            Individual coord1 = ontModel.getIndividual(baseURI + "coordinator" + ligacoes[1].replace(" ",""));
            if(nucleo1 != null && coord1 != null) nucleo1.addProperty(possui, coord1);
        }
        teste += "Total Nucleo -> Coordenador: "+cont+"\n";

//        //validar a nova ontologia a ser criada
//        InfModel modelInf = ModelFactory.createInfModel(reasoner, ontModel);
//        //reasoner = reasoner.bindSchema(ontModel); 
        //Gerar o novo arquivo com os dados do banco na nova ontologia
        FileWriter arquivo = null;
        try {
            //caminho para o novo arquivo de ontologia
            arquivo = new FileWriter(newontology);
            //se não tiver o arquivo vai criar, senão será reescrito
        } catch (IOException ex) {
        }
        
        BufferedWriter out = new BufferedWriter(arquivo);
        //utilizar RDF/XML-ABBREV, so RDF/XML da erro no protege!
        ontModel.write(out, "RDF/XML");
teste +=  "cabou o role";
        return teste;
    }   
}
