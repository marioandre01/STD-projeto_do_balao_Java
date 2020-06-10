/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author mario andre e mario allan
 */
public class PrincipalBaloes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        PrincipalBaloes ba = new PrincipalBaloes();
        
        //Nome do arquivo a ser passado para configurar um balao
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo com as configuraçoes da balao: ");
        String arqConfbal = teclado.nextLine();
        System.out.println("");
        
        //configurando caminho para acessar o arquivo
        String caminho = "src/balao/";
        String caminhoEnome;
        caminhoEnome = caminho + arqConfbal;
        
        
        //Lendo arquivo do balao e pegando conteudo da primeira linha
        String lConf = ba.lerConfBal(caminhoEnome);
        
        
        //colentando Id
        String idBal = ba.IdBal(lConf);
        //colentando valor da latitude
        double latBal = ba.getLatitudeBal(lConf);
        //colentando valor da longitude
        double longBal = ba.getLongitudeBal(lConf);
        //colentando valor da altitude
        double altBal = ba.getAltitudeBal(lConf);
        //colentando valor do id do vizinho
        String idBalViz = ba.getIdVizinhoBal(lConf);
        //colentando valor do arquivo das ERBs
        String arqErbBal = ba.getArqErbBal(lConf);
        
        //criando um balao
        Balao bal = new Balao(latBal, longBal, altBal, idBal, idBalViz, arqErbBal);
        //exibindo conteudo do balao
        bal.ExibirBaloes();
        //iniciando balao, começa a esperar um usuario se conectar
        bal.conectausuario();
    
    }
    
    //coleta conteudo da primeira linha do arquivo
    public String lerConfBal(String arq) {
        String linha = "";
        try {
            File arquivo; 
       
            arquivo = new File(arq);  
            FileInputStream fis = new FileInputStream(arquivo);  
  
            int ln;  
            while ( (ln = fis.read()) != -1 ) {  
                linha += (char)ln;
                if ((char)ln == '\n'){
                       break;
                }
            }  
            fis.close(); 
        }catch (IOException ee) {  
        
        }
        return linha;
    }
    
    //192.168.0.108:1550,-27.12243,48.2929,50.2050,192.168.0.120:1700,ERBs.txt
    public double getLatitudeBal(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        
        String latStr = valor.substring(posicao + 1, posicao2);
        double latInt = StringToDouble(latStr);
        
        return latInt;
    }
    
    //192.168.0.108:1550,-27.12243,48.2929,50.2050,192.168.0.120:1700,ERBs.txt
    public double getLongitudeBal(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        int posicao3 = valor.indexOf(",", posicao2+1);
        //int numCaract = valor.length();
        String longStr = valor.substring(posicao2 + 1, posicao3);
        double longInt = StringToDouble(longStr);
        
        return longInt;
    }
    
    //192.168.0.108:1550,-27.12243,48.2929,50.2050,192.168.0.120:1700,ERBs.txt
    public String IdBal(String valor){
        int posicao = valor.indexOf(",");
        
        String idStr = valor.substring(0, posicao);
        
        return idStr;
    }
    
    public double StringToDouble(String valor){
        try {
            return Double.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        }catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return 0;
        }
       
    }
    
    //192.168.0.108:1550,-27.12243,48.2929,50.2050,192.168.0.120:1700,ERBs.txt
    public double getAltitudeBal(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        int posicao3 = valor.indexOf(",", posicao2+1);
        int posicao4 = valor.indexOf(",", posicao3+1);
        
        String latStr = valor.substring(posicao3 + 1, posicao4);
        double latInt = StringToDouble(latStr);
        
        return latInt;
    }
    
    //192.168.0.108:1550,-27.12243,48.2929,50.2050,192.168.0.120:1700,ERBs.txt
    public String getIdVizinhoBal(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        int posicao3 = valor.indexOf(",", posicao2+1);
        int posicao4 = valor.indexOf(",", posicao3+1);
        int posicao5 = valor.indexOf(",", posicao4 + 1);
        //int numCaract = valor.length();
        
        String idVizStr = valor.substring(posicao4 + 1, posicao5);
        
        return idVizStr;
    }
    
    //192.168.0.108:1550,-27.695357,-48.825420,50.2055,192.168.0.108:1560,ERBs.txt
    public String getArqErbBal(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao + 1);
        int posicao3 = valor.indexOf(",", posicao2 + 1);
        int posicao4 = valor.indexOf(",", posicao3 + 1);
        int posicao5 = valor.indexOf(",", posicao4 + 1);
        int numCaract = valor.length();
        
        String arqErbStr = valor.substring(posicao5 + 1, numCaract -1);
        
        return arqErbStr;
    }
    
}
