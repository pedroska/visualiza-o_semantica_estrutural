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
import com.hp.hpl.jena.rdf.model.InfModel;
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
    //String path = "../../files/ontology/"
    private final String ontologia = path+"vaquinha.owl";    
    private final String newontology = path+"vaquinha_importacao.owl";
    private final String inferredontology = path+"vaquinha_inferred";
    
    public String loadSQL() throws SQLException {

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
        Resource resourceAnimal = ontModel.getResource(baseURI + "Vaca");
        Animal animal = new Animal();
        String animais = animal.buscaAnimais();
        String anims[] = animais.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < anims.length; cont++) {
            caracteristicas = anims[cont].split(";");
            ontModel.createIndividual(baseURI + "vaca" +caracteristicas[0].replace(" ", ""), resourceAnimal);
            Individual ind = ontModel.getIndividual(baseURI + "vaca" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeVaca, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeVaca, "vaca"+caracteristicas[0].replace(" ", ""));
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
            Individual ind = ontModel.getIndividual(baseURI + "consultor" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeConsultor, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeConsultor, "consultor"+caracteristicas[0].replace(" ", ""));
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
            Individual ind = ontModel.getIndividual(baseURI + "controleLeiteiro" +caracteristicas[0].replace(" ", ""));
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
        Resource resourceCoordenador = ontModel.getResource(baseURI + "Coordenador");
        Coordenador coordenador = new Coordenador();
        String coordenadores = coordenador.buscaCoordenadores();
        String coords[] = coordenadores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < coords.length; cont++) {
            caracteristicas = coords[cont].split(";");
            ontModel.createIndividual(baseURI + "coordenador" +caracteristicas[0].replace(" ", ""), resourceCoordenador);
            Individual ind = ontModel.getIndividual(baseURI + "coordenador" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeCoordenador, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeCoordenador, "coordenador"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Coordenadores: "+cont+"\n";
        
        //Fazendas
        Resource resourceFazenda = ontModel.getResource(baseURI + "Fazenda");
        Fazenda fazenda = new Fazenda();
        String fazendas = fazenda.buscaFazendas();
        String fazends[] = fazendas.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < fazends.length; cont++) {
            caracteristicas = fazends[cont].split(";");
            ontModel.createIndividual(baseURI + "fazenda" +caracteristicas[0].replace(" ", ""), resourceFazenda);
            Individual ind = ontModel.getIndividual(baseURI + "fazenda" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeFazenda, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeFazenda, "fazenda"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Fazendas: "+cont+"\n";
        
        //Instituicoes
        Resource resourceInstituicao = ontModel.getResource(baseURI + "Instituicao");
        Instituicao instituicao = new Instituicao();
        String instituicoes = instituicao.buscaInstituicoes();
        String instits[] = instituicoes.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < instits.length; cont++) {
            caracteristicas = instits[cont].split(";");
            ontModel.createIndividual(baseURI + "instituicao" +caracteristicas[0].replace(" ", ""), resourceInstituicao);
            Individual ind = ontModel.getIndividual(baseURI + "instituicao" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeInstituicao, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeInstituicao, "instituicao"+caracteristicas[0].replace(" ", ""));
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
            Individual ind = ontModel.getIndividual(baseURI + "nucleo" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeNucleo, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeNucleo, "nucleo"+caracteristicas[0].replace(" ", ""));
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
            Individual ind = ontModel.getIndividual(baseURI + "producao" +caracteristicas[0].replace(" ", ""));
            if(!caracteristicas[1].replace(" ", "").isEmpty() && !caracteristicas[1].replace(" ", "").equals("null")){
                ind.addProperty(DataInicioLactacao, caracteristicas[1].replace(" ", ""),XSDDatatype.XSDdate);
            }
            if(!caracteristicas[2].replace(" ", "").isEmpty() && !caracteristicas[2].replace(" ", "").equals("null")){
                ind.addProperty(DataFimLactacao, caracteristicas[2].replace(" ", ""),XSDDatatype.XSDdate);
            }
        }
        teste += "Total Produções/Lactação: "+cont+"\n";
        
        //Produtores
        Resource resourceProdutor = ontModel.getResource(baseURI + "Produtor");
        Produtor produtor = new Produtor();
        String produtores = produtor.buscaProdutores();
        String produts[] = produtores.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < produts.length; cont++) {
            caracteristicas = produts[cont].split(";");
            ontModel.createIndividual(baseURI + "produtor" +caracteristicas[0].replace(" ", ""), resourceProdutor);
            Individual ind = ontModel.getIndividual(baseURI + "produtor" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeProdutor, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeProdutor, "produtor"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Produtores: "+cont+"\n";
        
        //Racas
        Resource resourceRaca = ontModel.getResource(baseURI + "RacaVaca");
        Raca raca = new Raca();
        String racas = raca.buscaRacas();
        String racs[] = racas.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < racs.length; cont++) {
            caracteristicas = racs[cont].split(";");
            ontModel.createIndividual(baseURI + "raca" +caracteristicas[0].replace(" ", ""), resourceRaca);
            //Individual ind = ontModel.getIndividual(baseURI + "raca" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeRaca, caracteristicas[1].replace("  ", ""));
            //ind.addProperty(NomeRaca, "raca"+caracteristicas[0].replace(" ", ""));
        }
        teste += "Total Raças: "+cont+"\n";
        
        //Rebanhos
        Resource resourceRebanho = ontModel.getResource(baseURI + "Rebanho");
        Rebanho rebanho = new Rebanho();
        String rebanhos = rebanho.buscaRebanhos();
        String rebs[] = rebanhos.split("\n");
        //índice 0 é o cabeçalho, então começo do índice 1;
        for (cont = 1; cont < rebs.length; cont++) {
            caracteristicas = rebs[cont].split(";");
            ontModel.createIndividual(baseURI + "rebanho" +caracteristicas[0].replace(" ", ""), resourceRebanho);
            Individual ind = ontModel.getIndividual(baseURI + "rebanho" +caracteristicas[0].replace(" ", ""));
            //ind.addProperty(NomeRebanho, caracteristicas[1].replace("  ", ""));
            ind.addProperty(NomeRebanho, "rebanho"+caracteristicas[0].replace(" ", ""));
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
            Individual con = ontModel.getIndividual(baseURI + "consultor" +ligacoes[0].replace(" ", ""));
            Individual faz = ontModel.getIndividual(baseURI + "fazenda" + ligacoes[1].replace(" ",""));
            if(con != null && faz != null) con.addProperty(consulta, faz);
        }
        teste += "Total Consultores -> Fazendas: "+cont+"\n";
        
        //Propriedade de coordena (Coordenador)-[coordena]->(Consultor)
        String coord = coordenador.buscaConsultoresCoordenados();
        String coordenam[] = coord.split("\n");
        for (cont = 1; cont < coordenam.length; cont++) {
            ligacoes = coordenam[cont].split(";");
            Individual coord1 = ontModel.getIndividual(baseURI + "coordenador" +ligacoes[0].replace(" ", ""));
            Individual con1 = ontModel.getIndividual(baseURI + "consultor" + ligacoes[1].replace(" ",""));
            if(coord1 != null && con1 != null) coord1.addProperty(coordena, con1);
        }
        teste += "Total Coordenadores -> Consultores: "+cont+"\n";
        
        //Propriedade de hasControleLeiteiro (Producao)-[hasControleLeiteiro]->(ControleLeiteiro)
        String ctrl = producao.buscaControlesLeiteiros();
        String ctrls[] = ctrl.split("\n");
        for (cont = 1; cont < ctrls.length; cont++) {
            ligacoes = ctrls[cont].split(";");
            Individual lact1 = ontModel.getIndividual(baseURI + "producao" +ligacoes[1].replace(" ", ""));
            Individual ctrl1 = ontModel.getIndividual(baseURI + "controleLeiteiro" + ligacoes[2].replace(" ",""));
            if(lact1 != null && ctrl1 != null) lact1.addProperty(hasControleLeiteiro, ctrl1);
        }
        teste += "Total Produção -> Controle Leiteiro: "+cont+"\n";

        //Propriedade de produção (Vaca)-[hasProducao]->(produção)
        String hasprods = producao.buscaControlesLeiteiros();
        String hasproducoes[] = hasprods.split("\n");
        for (cont = 1; cont < hasproducoes.length; cont++) {
            ligacoes = hasproducoes[cont].split(";");
            Individual vaca = ontModel.getIndividual(baseURI + "vaca" +ligacoes[0].replace(" ", ""));
            Individual prod = ontModel.getIndividual(baseURI + "producao" + ligacoes[1].replace(" ",""));
            if(vaca != null && prod != null) vaca.addProperty(hasProducao, prod);
        }        
        teste += "Total Animais -> Produção: "+cont+"\n";
        
        //Propriedade de produção (Rebanho)-[hasVaca]->(vaca)
        String hasanims = rebanho.buscaAnimaisRelacionados();
        String hasanimais[] = hasanims.split("\n");
        for (cont = 1; cont < hasanimais.length; cont++) {
            ligacoes = hasanimais[cont].split(";");
            Individual reb = ontModel.getIndividual(baseURI + "rebanho" +ligacoes[0].replace(" ", ""));
            Individual vaca = ontModel.getIndividual(baseURI + "vaca" + ligacoes[1].replace(" ",""));
            if(vaca != null && reb != null) reb.addProperty(hasVaca, vaca);
        }        
        teste += "Total Rebanhos -> Animais: "+cont+"\n";

        //Rebanhos de uma fazeda (Fazenda)-[hasRebanho]->(rebanho)
        String hasrebs = fazenda.buscaRebanhosRelacionados();
        String hasrebanhos[] = hasrebs.split("\n");
        for (cont = 1; cont < hasrebanhos.length; cont++) {
            ligacoes = hasrebanhos[cont].split(";");
            Individual reb = ontModel.getIndividual(baseURI + "rebanho" +ligacoes[1].replace(" ", ""));
            Individual faz = ontModel.getIndividual(baseURI + "fazenda" + ligacoes[0].replace(" ",""));
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
            Individual nucleo1 = ontModel.getIndividual(baseURI + "nucleo" +ligacoes[0].replace(" ", ""));
            Individual coord1 = ontModel.getIndividual(baseURI + "coordenador" + ligacoes[1].replace(" ",""));
            if(nucleo1 != null && coord1 != null) nucleo1.addProperty(possui, coord1);
        }
        teste += "Total Núcleo -> Coordenador: "+cont+"\n";

        //validar a nova ontologia a ser criada
        InfModel modelInf = ModelFactory.createInfModel(reasoner, ontModel);
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
        //utilizar RDF/XML-ABBREV, so RDF/XML da erro no protege!
        ontModel.write(out, "RDF/XML");
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
