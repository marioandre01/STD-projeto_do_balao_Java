/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author mario andre e mario allan
 */
public class Balao {
    
    //Atributos
    double longitude;
    double latitude;
    double altitude;
    String iDUnicoBalao;
    String iDUnicoVizinho;
    String arqErbs;
    
    //Metodos
    
    //Metodo construtor padrão da classe Balão
    public Balao() {
        
    }
    
    //Metodo construtor de seis parametros
    public Balao(double latitude, double longitude, double altitude, String iDUnicoBalao, String iDUnicoVizinho, String arqErbs) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.iDUnicoBalao = iDUnicoBalao;
        this.iDUnicoVizinho = iDUnicoVizinho;
        this.arqErbs = arqErbs;
        
    } 
    
    //Exibi as variaveis criadas de um balão
    public void ExibirBaloes() {
        System.out.println("### Dados do Balao ###");
        System.out.println("----------------------");
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println("Altitude: " + altitude);
        System.out.println("Id do Balao: " + iDUnicoBalao);
        System.out.println("Id do Vizinho: " + iDUnicoVizinho);
        System.out.println("Arquivo da localizaçao das ERBs: " + arqErbs);
        System.out.println("----------------------");
        System.out.println("");

    }
    
    //metodo que inicia o balao, fica esperando um usuario ou um balao se conectar
    public void conectausuario() {
        
        //pegando porta do balao
        int porta = getPortaBal(this.iDUnicoBalao);
       
        //verifica o numero de linhas do arquivo das ERBs
        int numl = numLinhaArq(arqErbs);
        
        //criando vetor de numl posiçoes
        String dadosErb[] = new String[numl];
        
        //Lendo cada linha do arquivo dos dados da ERB e colocando na posiçao do vetor
        linhaArqToVetor(dadosErb, dadosErb.length, arqErbs);
        
        //Lendo conteudo do vetor
        //lerVetor(dadosErb);
        
        try{
            //criando socket para conectar um usuario ou balao
            ServerSocket servidor = new ServerSocket(porta);
            System.out.println("Aguardando por conexoes de usuarios ou baloes...");
            while(true){
            
                Socket conexao = servidor.accept(); //fica esperando um usuario ou balao se conectar
                Thread t = new BalaoThread(conexao, latitude, longitude, iDUnicoVizinho, dadosErb);
                t.start();
            }
        }catch (IOException ex) {
            System.err.println("Erro sockets: " + ex.toString());
        }
    }
    
    public int StringToInt(String valor){
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        }catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return 0;
        }
        
    }
    
    //exemplo: 192.168.0.108:100"
    public int getPortaBal(String valor){
        
        int posicao = valor.indexOf(":");
        int numCaract = valor.length();
        String portaStr = valor.substring(posicao + 1, numCaract);
        int portaInt = StringToInt(portaStr);
        
        return portaInt;
    }
    
    //metodo que le cada linha do arquivo dos dados da ERB e colocando na posiçao do vetor
    public void linhaArqToVetor(String d[], int nl, String arq) {
        int i;
        for(i=0; i < nl;i++){
            d[i]= "";
        }
        
        try {
            String caminho = "src/balao/";
            String caminhoEarq = caminho + arq;
            File arquivo; 
        
            // Lendo do arquivo  
            arquivo = new File(caminhoEarq);  
            FileInputStream fis = new FileInputStream(arquivo);  
            
            //coletando caracteres do arquivo e coloca numa posiçao do vetor 
            i = 0;
            int ln;  
            while ( (ln = fis.read()) != -1 ) {  
                if ((char)ln != '\n'){
                    d[i] += (char)ln;
                }else{
                    i++;
                }
            }  
            fis.close(); 
        }catch (IOException ee) {  
        
        }
        
    }
    
    //metodo que retorna o numero de linhas do arquivo fornecido
    public int numLinhaArq(String arq) {
        int n = 0;
        
        try {
            //definindo caminho do arquivo
            String caminho = "src/balao/";
            String caminhoEarq = caminho + arq;
            
            File arquivo; 
        
            // Lendo do arquivo  
            arquivo = new File(caminhoEarq);  
            FileInputStream fis = new FileInputStream(arquivo);  
                
            int ln;  
            while ( (ln = fis.read()) != -1 ) {  
                
                if ((char)ln == '\n'){
                    n++;
                }
            }  
            fis.close(); 
        }catch (IOException ee) {  
        
        }
        return n;
    }
    
    //metodo que le conteudo de um vetor
    public void lerVetor(String v[]) {
        int x;
        for(x=0; x < v.length ;x++){
            System.out.println("linha "+ x +":" + v[x]);
        }
    }
    
}
            
          
    

