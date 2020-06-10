/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balao;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author mario andre e mario allan
 */
public class BalaoThread extends Thread{
    
    private Socket conexao;
    double latitude;
    double longitude;
    String iDUnicoVizinho;
    String linhasArqErbs[];
    boolean estaPerto = false;
    
    public BalaoThread(Socket c){
       this.conexao = c;
    }
    
    //construtor de cinco parametros
    public BalaoThread(Socket c, double lat, double longi, String idViz, String [] lArqE){
       this.conexao = c;
       this.latitude = lat;
       this.longitude = longi;
       this.iDUnicoVizinho = idViz;
       this.linhasArqErbs = lArqE;
    }
    
    
    public void run() {
        try {
            System.out.println("");
            System.out.println("");
            //System.out.println("Thread Balao executada");
            System.out.println("Cliente ou balao se conectou --- " + conexao);
            System.out.println("");
    
            //pega o indice do vetor que contem os dados da ERb que esta perto do balao
            int indVetor;
            indVetor = indiceErbPerto();
            
            System.out.println("** Verificando se ha alguma ERB por perto **");
            System.out.println("--------------------------------------------");
            
            //se estaPerto for true entao tem erb perto do balao
            if(estaPerto == true){
                
                //envia mensage dizendo q tem erb perto do balao
                enviaMensagem(conexao, "sim");
                
                //pegando ip da erb
                String ipErb = getIpErb(linhasArqErbs[indVetor]);
                //pegando porta da erb
                int portaErb = getPortaErb(linhasArqErbs[indVetor]);
                
                System.out.println("ERB encontrada");
                System.out.println("IP:"+ipErb+" Porta:"+portaErb);
                System.out.println("Se conectando na ERb... ");
                System.out.println("");
                
                //balao se conecta na erb
                Socket sErb = conectaErb(ipErb, portaErb);
                
                System.out.println("Aguardando envio do arquivo... ");
                System.out.println("");
                
                //repassa os bytes do arquivo enviados pelo usuario para a erb
                repassaArquivo(conexao, sErb);
                
            }else{ //senao vrifica se balao tem vizinho
                
                System.out.println("Nenhuma ERB encontrada");
                System.out.println("--------------------------------------------");
                System.out.println("");
                
                System.out.println("** Verificando se balao possui vizinho **");
                System.out.println("-----------------------------------------");
                
                //verifica se balao tem vizinho
                boolean pv ;
                pv = possuiVizinho(iDUnicoVizinho);
                
                //se pv for false balao nao tem vizinho
                if(pv == false){
                    System.out.println("Balao nao tem vizinho");
                    enviaMensagem(conexao, "nao");
                    
                }else{ //senao possui vizinho
                    
                    //pega ip do balao vizinho
                    String ipBalViz = getIpBal(iDUnicoVizinho);
                    //pega porta do balao vizinho
                    int portaBalViz = getPortaBal(iDUnicoVizinho);
                    
                    System.out.println("Vizinho encontrado");
                    System.out.println("IP:"+ipBalViz+" Porta:"+portaBalViz);
                    System.out.println("");
                    
                    //balao se conecta no seu balao vizinho
                    Socket sViz = conectaViz(ipBalViz, portaBalViz);
                    
                    //balao recebe mensagem do balao vizinho
                    String m = recebeMensagem(sViz);
                    
                    //se mensagem for "sim" balao vizinho encontrou uma erb
                    if(m.equals("sim") == true){
                        System.out.println("Balao vizinho encontrou uma ERB");
                        System.out.println("");
                        System.out.println("Aguardando envio do arquivo... ");
                        System.out.println("");
                        
                        enviaMensagem(conexao, "sim");
                        repassaArquivo(conexao, sViz);
                        
                    }else{ //senao nao encontrou erb
                        
                        System.out.println("Balao Vizinho nao encontrou nenhuma ERB");
                        enviaMensagem(conexao, "nao2");
                    }
                    
                }
               
            }
            System.out.println("-----------------------------------------");
            
            conexao.close();
            
        } catch (IOException ex) {
            System.err.println("Erro sockets: " + ex.toString());
        }
   
    }
    
    public Socket conectaErb(String ipErb, int portaErb) {
        Socket s1 = null;
        
        //Faz conexao com a ERB via socket
        try{
            Socket servidorErb = new Socket(ipErb,portaErb);
            System.out.println("Conectado na ERB --- " + servidorErb);
            System.out.println("");
            s1 = servidorErb;
            
        }catch (IOException ex) {
           System.err.println("Erro sockets: " + ex.toString());
        }
        return s1;
    }
    
    public Socket conectaViz(String ipErb, int portaErb) {
        Socket s1 = null;
        //Faz conexao com a ERB via socket
        try{
            Socket balVizinho = new Socket(ipErb,portaErb);
            System.out.println("Conectado no Balao vizinho --- " + balVizinho);
            System.out.println("");
            s1 = balVizinho;
            
        }catch (IOException ex) {
           System.err.println("Erro sockets: " + ex.toString());
        }
        return s1;
    }
    
    
    public String recebeMensagem(Socket c) {
        String mensagem = null;
        
        try{
            // Estabelece fluxo de entrada
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(c.getInputStream()));
            
            //Recebe mensagem
            mensagem = fluxoEntrada.readUTF();
          
        }catch (IOException ex) {
            System.err.println("Erro sockets: " + ex.toString());
            
        }
        return mensagem;
    }
    
    public void enviaMensagem(Socket c, String mensagem){
        
        try{
            // Estabelece fluxos de saida para enviar string
            DataOutputStream fluxoSaida = new DataOutputStream(c.getOutputStream());
            
            // Envia mensagem
            fluxoSaida.writeUTF(mensagem);
            fluxoSaida.flush();
            
        }catch (IOException ex) {
            System.err.println("Erro sockets: " + ex.toString());
        }
        
    }
    
    public void repassaArquivo(Socket usu, Socket erb) throws IOException{
            
        //Recebendo nome do arquivo do usuario
        String nomeArq;
        nomeArq = recebeMensagem(usu);
        //System.out.println("nome do arquivo: " + nomeArq);
         
        // Estabelece fluxos de saida para enviar nome do arquivo para a ERB
        DataOutputStream fluxoSaida = new DataOutputStream(erb.getOutputStream());
        
        // Envia nome do arquivo para a ERB
        fluxoSaida.writeUTF(nomeArq);
        fluxoSaida.flush();
        
        //Recebendo bytes do arquivo do usuario
        try {
            //Criando fluxo de dados para leitura entre o balao e o cliente
            InputStream recebe = usu.getInputStream();
        
            // Prepara variaveis para transferencia
            byte[] cbuffer = new byte[1024];
            int bytesRead;
            
            // Criando canal de transferencia entre balao e a ERB
            OutputStream envia;
            envia = erb.getOutputStream();
        
            // Copiando conteudo do canal usuario <-> balao e enviando para o canal balao <-> ERB
             System.out.println("### Repassando novo arquivo ###");
            System.out.println("--------------------------------");
            System.out.println("Recebendo arquivo "+nomeArq+" ...");
            while ((bytesRead = recebe.read(cbuffer)) != -1) {
                envia.write(cbuffer, 0, bytesRead);
                envia.flush();
            }
            System.out.println("Arquivo encaminhado!");
            System.out.println("--------------------------------");
            System.out.println("");
                   
            envia.close();
            recebe.close();
        }catch (IOException e) {
            //e.printStackTrace();
        } 
    }
    
    //Metodo que calcula a distancia entre dois pontos geograficos
    public double Distancia(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = deg2rad(lat2 - lat1);
        Double lonDistance = deg2rad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        double distance = R * c * 1000; // convert to meters
       
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    //Metodo que é utilizado pelo metodo Distancia para calcular a ditancia entre dois pontos
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    public double getLatitudeErb(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        
        String latStr = valor.substring(posicao + 1, posicao2);
        double latInt = StringToDouble(latStr);
        
        return latInt;
    }
    
    public double getLongitudeErb(String valor){
        int posicao = valor.indexOf(",");
        int posicao2 = valor.indexOf(",", posicao+1);
        int numCaract = valor.length();
        String longStr = valor.substring(posicao2 + 1, numCaract);
        double longInt = StringToDouble(longStr);
        
        return longInt;
    }
    
    public double StringToDouble(String valor){
        try {
            return Double.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        }catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return 0;
        }
       
    }
    
    //metodo que pega o indice do vetor q possui os addos da erb q esta prto do balao
    public int indiceErbPerto(){
        
            int j;
            int iv = 0;
            for(j=0; j < linhasArqErbs.length; j++ ){
                
                //pegando o valor da latitude e longitude da erb
                double latErb = getLatitudeErb(linhasArqErbs[j]);
                double longErb = getLongitudeErb(linhasArqErbs[j]);
                
                //Distancia(double lat1, double lat2, double lon1, double lon2)
                double distBalErb = Distancia(latitude, latErb, longitude, longErb);
                
                //Se a distancia entre o balao e a ERB for menor ou igual a 40000 metros, entao se conecta na ERB
                if(distBalErb <= 40000){
                    estaPerto = true;
                    iv = j;
                    break;
                }
            }
            return iv;
    }
    
    public String getIpErb(String valor){
       
        int posicao = valor.indexOf(":");
        String ipStr = valor.substring(0, posicao);
        
        return ipStr;
    }
    
    public int getPortaErb(String valor){
        
        int posicao = valor.indexOf(":");
        int posicao2 = valor.indexOf(",");
        //int numCaract = valor.length();
        String portaStr = valor.substring(posicao + 1, posicao2);
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
    
     public boolean possuiVizinho(String valor){
        boolean p = true;
        if (valor.length() == 1){
            p = false;
        }
        return p;
     }
     
    //exemplo: 192.168.0.108:100"
    public String getIpBal(String valor){
       
        int posicao = valor.indexOf(":");
        String ipStr = valor.substring(0, posicao);
        
        return ipStr;
    }
    
    //exemplo: 192.168.0.108:100"
    public int getPortaBal(String valor){
        
        int posicao = valor.indexOf(":");
        int numCaract = valor.length();
        String portaStr = valor.substring(posicao + 1, numCaract);
        int portaInt = StringToInt(portaStr);
        
        return portaInt;
    }

}
