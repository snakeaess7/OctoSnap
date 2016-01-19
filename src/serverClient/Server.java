/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverClient;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import serverThread.serverThread;

/**
 *
 * @author Delibor
 */
public class Server {
	public static final int PORT = 1337;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
                ServerSocket ss = new ServerSocket(PORT);
               
                
               
        System.out.println("Server startovan \n");  
        for(int i=0;;++i){
            
           
            Socket sock = ss.accept();
             System.out.println(sock.getInetAddress()+"\n");
            serverThread st = new serverThread(sock);
             System.out.println("zahtjev br "+i+"\n");
        }
        
        //finally{this.finalize();};
    }
    
  
    
      
    }
    

