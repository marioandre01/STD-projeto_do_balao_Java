/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author mario andre e mario allan
 */
public class PrincipalErb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrincipalErb pe = new PrincipalErb();
        
        //Nome do arquivo a ser passado para configurar a erb
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo com as configuraçoes da ERB: ");
        String arqConfErb = teclado.nextLine();
        System.out.println("");
        
        //Lendo arquivo das ERBs e pegando conteudo da primeira linha
        String lConf = pe.lerConfErb(arqConfErb);
        //colentando Id
        String idErb = pe.IdErb(lConf);
        //colentando valor da latitude
        double latErb = pe.getLatitude(lConf);
        //colentando valor da longitude
        double longErb = pe.getLongitude(lConf);
        
        //cria uma ERB
        Erb eb1 = new Erb(latErb, longErb, idErb);
        //exibe os dados da erb criada
        eb1.ExibirErb();
        //inicia erb, ela fica aguardando um balao se conectar
        eb1.conectaBalao();
    }
    
    //pega primeira linha do arquivo e coloca em uma string
    public String lerConfErb(String arq) {
        String linha = "";
        try {
            File arquivo; 
        
            //definindo caminho onde esta o arquivo  
            String caminho = "src/erb/";
            String caminhoEnome;
            caminhoEnome = caminho + arq;
            
            //abrindo o arquivo
            arquivo = new File(caminhoEnome);  
            FileInputStream fis = new FileInputStream(arquivo);  
            
            //coletando caracteres do arquivo
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
    
    //converte uma string para double
    public double StringToDouble(String valor){
        try {
            return Double.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        }catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return 0;
        }
       
    }
    
    //192.168.0.108:1500,-27.704002,-48.805369
    public double getLatitude(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        
        String latStr = valor.substring(posicao + 1, posicao2);
        double latInt = StringToDouble(latStr);
        
        return latInt;
    }
    
    //192.168.0.108:1500,-27.704002,-48.805369
    public double getLongitude(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        int numCaract = valor.length();
        
        String longStr = valor.substring(posicao2 + 1, numCaract);
        double longInt = StringToDouble(longStr);
        
        return longInt;
    }
    
    //192.168.0.108:1500,-27.704002,-48.805369
    public String IdErb(String valor){
        int posicao = valor.indexOf(",");
        
        String idStr = valor.substring(0, posicao);
        
        return idStr;
    }
    
}
