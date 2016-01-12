/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Delibor
 */
public class Client extends Thread {

    public static final int PORT = 1337;
    public static final String IP = "127.0.0.1";
    public static final String LOGIN = "0:123321:TEST";
    public static final String SEND = "2";
    public static Object SLIKA;
    private Socket sock;
    private ObjectInputStream in;
    private PrintWriter out;

    {
        try {
            sock = new Socket(IP, PORT);

            in
                    = new ObjectInputStream(
                            sock.getInputStream());
            out
                    = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            sock.getOutputStream())), true);

        } catch (IOException ex) {
            System.out.println("OFFLINE! \n");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() //throws IllegalAccessError
    {
        login();
        try {
         

            //wait();
            sleep(1000);

            send();
            sleep(1000);
            send();
            sleep(1000);
            send();
           
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean login() {
        try {

            // inicijalizuj izlazni stream
            System.out.println(LOGIN);
            out.println(LOGIN);

            String status = (String)in.readObject();
            System.out.println(status);

            if ((status != null) && status.contains("uspjesna!")) {
                //za svaki slučaj
                return true;
                /*notifyAll()/*System.out.println(status)*/
            }//Može neki print
            else if (status == null) {
                System.out.println("Neuspjesan login \n");

                return false;//throw new IllegalAccessError();

            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        System.out.println("Greska \n");

        return false;
    }

    public void send() throws IOException, ClassNotFoundException {
        {
            


            out.println("2:TEST");
            

            ConcurrentHashMap<String, String> LOGGEDIN = (ConcurrentHashMap<String, String>) in.readObject();
            System.out.println(LOGGEDIN.toString());
            Socket sock = new Socket(InetAddress.getByName(IP), PORT);

          
        }
    }

}
