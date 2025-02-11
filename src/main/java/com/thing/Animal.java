package com.thing;

import com.utils.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Pedro Ivo
 */
public class Animal {
    private static String todos = "";
    
    public String buscaAnimais() throws SQLException{
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        //String sql = "SELECT DISTINCT animal.id_animal, animal.nome_animal "
        //            + "FROM animal INNER JOIN lactacao on animal.id_animal = lactacao.id_animal "
        //            + "WHERE animal.sexo = 'f' AND vivo is not false AND data_nascimento > '01/01/2014'";
        String sql ="SELECT * FROM animal WHERE id_animal IN(3484,3495,3497,3498,3499,3501,3502,3503,3504,3507,4191,4214,4215,4217,4219,4225,4235,4256,4257,4321,4330,4332,4336,4338,4339,4340,\n" +
"4360,4366,4367,4380,4407,4409,4415,4416,4419,4421,4423,4424,4425,4428,4433,4444,4526,5214,5215,5216,6500,6590,6591,6593,6802,7159,9123,\n" +
"9124,9127,9131,9132,9136,9139,9142,9146,9148,9151,9153,9154,9156,9157,9159,9164,9167,9168,9645,9909,10591,11457,12278,13955,15507,15508,\n" +
"15509,15999,17037,17069,17075,17077,17079,17082,17305,17306,17310,17315,17577,17578,17999,19967,19996,20010,20013,20014,20015,20018,20019,\n" +
"20020,20028,20034,20038,20040,20046,20053,20057,20063,20068,20069,20074,20081,20084,20092,20103,20104,20108,20110,20112,20115,20117,20118,\n" +
"20120,20122,20125,20129,20130,20133,20136,20139,20140,20185,20186,20202,20238,20239,20243,20246,20248,20250,20253,20257,20258,20261,20262,\n" +
"20266,20268,20269,20497,20740,20743,20745,20746,20751,20915,20916,20919,21859,21860,21862,21863,21864,21866,21867,21873,21874,21878,21882,\n" +
"21890,21893,21911,22064,22065,22356,22538,22540,22545,22547,22549,22551,22552,22553,22554,22555,22631,22636,22641,22642,22643,22644,22646,\n" +
"23385,27905,27906,27907,27908,27909,27910,27911,27946,27947,27948,27949,27950,27951,27952,27953,27954,27955,27956,27957,27958,27959,29724,\n" +
"29801,29811,29851,29853,29857,29863,29867,29871,29874,29884,29912,29917,29922,29924,29931,29940,29942,29948,29952,29997,30001,30003,30010,\n" +
"30021,30024,30027,30029,30036,30039,30042,30047,30049,30052,30054,30057,30059,30069,30070,30072,30073,30076,30077,30088,30089,30090,30091,\n" +
"30092,30093,30094,30096,30097,30104,30111,30112,30113,30114,30115,30116,30117,30118,30119,30120,30121,30123,30125,30126,30127,30129,30153,\n" +
"30154,30156,30160,30165,30166,30169,30205,30208,30209,30220,30278,30291,30343,30360,30370,30383,30384,30387,30389,30394,30399,30406,30420,\n" +
"30428,30450,30485,30486,30488,30497,30507,30510,30515,30518,30519,30527,30528,30529,30533,30536,30541,30542,30543,30545,30551,30555,30558,\n" +
"30559,30561,30563,30585,30586,30587,30588,30589,30591,30593,30596,30602,30608,30638,30641,30643,30695,30696,32296,36532)";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        todos = String.format("%10s;%50s \n","id_animal","nome_animal");
        
        while(rs.next()){
            todos = todos.concat(String.format("%10s;%50s \n",rs.getString("id_animal"),rs.getString("nome_animal")));
        }
        
        return todos;
    }
    
    public String buscaProducoes() throws SQLException{
        String producao;
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT a.id_animal, l.id_lactacao "
                    + "FROM animal a "
                    + "INNER JOIN lactacao l ON a.id_animal = l.id_animal "
                    + "AND l.data_inicio = (SELECT MAX(data_inicio) from lactacao "
                    + "                            WHERE lactacao.id_animal = a.id_animal)"
                    + "WHERE a.sexo = 'f' AND a.vivo is not false AND a.data_nascimento > '01/01/2014'";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        producao = String.format("%10s;%60s \n","id_animal","id_lactacao");
        
        while(rs.next()){
            producao = producao.concat(String.format("%10s;%60s \n",rs.getString("id_animal"),rs.getString("id_lactacao")));
        }
        
        return producao;
    }
    
    public String buscaRacas() throws SQLException{
        String raca;
        
        ResultSet rs;
        Conexao conex = new Conexao();
        Connection conexao = conex.getConn();
        
        String sql = "SELECT a.id_animal, a.id_raca "
                    + "FROM animal a "
                    + "WHERE a.sexo = 'f' AND a.vivo is not false AND a.data_nascimento > '01/01/2014'AND a.id_raca is not null";
        Statement stm = conexao.createStatement();
        rs = stm.executeQuery(sql);
        
        raca = String.format("%10s;%60s \n","id_animal","id_lactacao");
        
        while(rs.next()){
            raca = raca.concat(String.format("%10s;%60s \n",rs.getString("id_animal"),rs.getString("id_raca")));
        }
        
        return raca;
    }
}
