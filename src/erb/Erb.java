/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author mario andre e mario allan
 */
public class Erb {
    
    //Atributos
    double latitude;
    double longitude;
    String iDUnicoERB;
    
    //Metodo construtor padrão da classe Erb
    public Erb() {
        
    }
    
    //Metodo construtor de tres parametros
    public Erb(double latitude, double longitude, String iDUnicoERB) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.iDUnicoERB = iDUnicoERB;
        
    }
    
    //Exibi as variaveis criadas de uma Erb
    public void ExibirErb() {
        System.out.println("### Dados da ERB ###");
        System.out.println("--------------------");
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println("Id da ERB: " + iDUnicoERB);
        System.out.println("--------------------");
        System.out.println("");

    }
    
    //metodo para iniciar uma erb, fica esperando um balao se conectar
    public void conectaBalao() {
       
        int porta = getPortaErb(this.iDUnicoERB);
        
        try{
            ServerSocket servidorErb = new ServerSocket(porta);
            System.out.println("Aguardando por conexoes de baloes...");
            while(true){
            
                Socket conexao = servidorErb.accept();
                Thread t = new ErbThread(conexao);
                t.start();
            }
        }catch (IOException ex) {
            System.err.println("Erro sockets: " + ex.toString());
        }
    }
    
    public int getPortaErb(String valor){
        
        int posicao = valor.indexOf(":");
        int numCaract = valor.length();
        String portaStr = valor.substring(posicao + 1, numCaract);
        int portaInt = StringToInt(portaStr);
        
        return portaInt;
    }
    
    public int StringToInt(String valor){
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        }catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return 0;
        }
    }
}
