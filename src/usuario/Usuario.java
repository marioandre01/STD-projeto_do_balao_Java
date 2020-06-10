/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author mario andre e mario allan
 */
public class Usuario {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       
        Usuario u = new Usuario();
        
        System.out.println("#### Digite o IP e a Porta do balao que se dejese se conectar #### ");
        System.out.println("-------------------------------------------------------------------");
        
        //coletando ip do balao
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ip do balao: ");
        String ipBalao = teclado.nextLine();
        
        //coletando porta do balao
        System.out.print("Porta do balao: ");
        int portaBalao = teclado.nextInt();
        System.out.println("-------------------------------------------------------------------");
        System.out.println("");
        
        //Faz conexao com o servidor-balao via socket
        try{
            //cria socket para se conectar no balao
            Socket conexaoServidor = new Socket(ipBalao, portaBalao);
            System.out.println("Conectado no Balao --- " + conexaoServidor);
            System.out.println("");
        
            //recebendo mensagem do balao
            String men = u.recebeMensagem(conexaoServidor);
            
            System.out.println("### Envio de arquivo ###");
            System.out.println("------------------------");
            
            //decidindo o que fazer dependendo da mensagem recebida do balao
            if(men.equals("nao") == true){
                System.out.println("Recusado, balao nao tem ERB por perto e nao possui balao vizinho!");
            
            }else if(men.equals("nao2") == true){
                System.out.println("Recusado, balao nao tem ERB por perto, mas possui balao vizinho, so que ele tambem nao tem ERB por perto");
            }else{
                //chama metodo para enviar aquivo para o balao
                u.EnviaArquivo(conexaoServidor);
            }
            
            System.out.println("------------------------");
            
            conexaoServidor.close(); //fecha conexao do socket
            
        }catch(IOException ex){
            System.err.println("Erro sockets: " + ex.toString());
        }    
    }
    
    public void EnviaArquivo(Socket c) throws IOException{
        
        //cria obeto para usar o teclado
        Scanner teclado = new Scanner(System.in);
        
        //Nome do arquivo a ser enviado
        System.out.print("Digite o nome do arquivo a ser enviado ao servidor: ");
        String NomeArquivo = teclado.nextLine();
        System.out.println("");
        
        // Estabelece fluxos de saida para enviar string
        DataOutputStream fluxoSaida = new DataOutputStream(c.getOutputStream());
        
        // Envia nome do arquivo 
        fluxoSaida.writeUTF(NomeArquivo);
        fluxoSaida.flush();
        
        //Prepara caminho para acessar arquivo
        String caminho = "src/usuario/";
        String nomeEcaminho = caminho + NomeArquivo;

        // Criando tamanho de leitura
        byte[] cbuffer = new byte[1024];
        int bytesRead;
        
        // Criando arquivo que sera enviado para o servidor
        File file = new File(nomeEcaminho);
        OutputStream envia;
        try{
            FileInputStream fileIn = new FileInputStream(file); 
            System.out.println("Lendo arquivo...");
                    
            // Criando canal de transferencia
            envia = c.getOutputStream();
                    
            // Lendo arquivo criado e enviado para o canal de transferencia
            System.out.println("Enviando Arquivo...");
            while ((bytesRead = fileIn.read(cbuffer)) != -1) {
                envia.write(cbuffer, 0, bytesRead);
                envia.flush();
                
            }       
            System.out.println("Arquivo Enviado!");
                
            //envia.close();
            //c.close();
                
        }catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Algum erro ocorreu!");
        }
    }
    
    public String recebeMensagem(Socket c) {
        String mensagem = null;
        // Estabelece fluxo de entrada
        try{
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(c.getInputStream()));
            
            //Recebe mensagem do servidor-balao
            mensagem = fluxoEntrada.readUTF();
          
        }catch (IOException ex) {
            System.err.println("Erro sockets: " + ex.toString());
            
        }
        return mensagem;
    }
    
}
