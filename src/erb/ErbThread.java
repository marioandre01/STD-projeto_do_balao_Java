/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erb;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 * @author mario andre e mario allan
 */
public class ErbThread extends Thread{
    private Socket conexao;
    
    public ErbThread(Socket c){
        this.conexao = c;
    }
    
    public void run() {
        System.out.println("");
        //System.out.println("Thread ERB executada");
        System.out.println("Balao conectou --- " + conexao);
        System.out.println("");
        System.out.println("Aguardando envio do arquivo... ");
        System.out.println("");
        
        //recebe arquivo do balao
        receberArquivo(conexao);
        
        //conexao.close();
    }
    
    //metodo para receber mensagem do balao
    public String recebeMensagem(Socket c) {
        String mensagem = null;
        // Estabelece fluxo de entrada
        try{
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(c.getInputStream()));
            
            //Recebe nome do arquivo
            mensagem = fluxoEntrada.readUTF();
               
        }catch (IOException ex) {
            System.err.println("Erro sockets: " + ex.toString());
            
        }
        return mensagem;
    }
    
    //metodo para receber arquivo do balao
    public void receberArquivo(Socket c){
            
        //Recebendo nome do arquivo balao
        String nomeArq;
        nomeArq = recebeMensagem(c);
            
        //Definindo local para salvar o arquivo
        String caminho = "src/erb/";
        String nomeEcaminho = caminho + nomeArq;
        
        //Recebendo arquivo do balao
        try {
            //Criando fluxo de dados para leitura entre servidor e cliente
            InputStream recebe = c.getInputStream();
        
            // Cria arquivo local no servidor
            FileOutputStream fos = new FileOutputStream(new File(nomeEcaminho));
            System.out.println("### Recebendo novo arquivo ###");
            System.out.println("-------------------------------");
            //System.out.println("Arquivo Local Criado");
        
            // Prepara variaveis para transferencia
            byte[] cbuffer = new byte[1024];
            int bytesRead;
        
            // Copiando conteudo do canal
            System.out.println("Recebendo arquivo "+nomeArq+" ...");
            while ((bytesRead = recebe.read(cbuffer)) != -1) {
                fos.write(cbuffer, 0, bytesRead);
                fos.flush();
                
            }
            System.out.println("Arquivo recebido!");
            System.out.println("-------------------------------");
            System.out.println("");
                   
            fos.close();
            recebe.close();
        }catch (IOException e) {
            //e.printStackTrace();
        } 
    }

}
