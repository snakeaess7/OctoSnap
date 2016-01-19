/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.FxmlLoader;
import Controller.IMGSENDController;
import Controller.LOGINController;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.VoidType;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Delibor
 */
public class Client extends Thread {

    public static final int PORT = 1337;
    public static final String IP = "127.0.0.1";
    public String LOGIN = "0:123321:TEST";
    public static String SEND = "2";
    public String name = "test";
    public static Object SLIKA;
    private Socket sock;
    public static boolean stanje;
    private ObjectInputStream in;
    private PrintWriter out;
    public static ConcurrentHashMap<String, String> LOGGEDIN;
    public static Socket sockusr;

    public Client(String stat, String key, String name) {
        this.name = name;
        LOGIN = stat + ":" + key + ":" + name;
    }

   
    @Override
    public void run() //throws IllegalAccessError
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
            if ((Controller.LOGINController.succ = login()).equals("Prijavljeno!")) {

                //wait();
                ServerSocket rec = new ServerSocket(PORT + 1);//PORT FORWARDING treba za internet
                for (;;) {
                    sockusr = rec.accept();
                    System.out.println("Zahtjev primljen.");
                   
                   Platform.runLater(() -> OCTOSNAP.HACK.rec());
                    
                }
                 
            } else {
                return;
            }

        } catch (IOException ex) {
            Controller.LOGINController.succ = "NOT CONNECTED OR KEY IN USE";
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        return;
    }

    public String login() {
        try {

            // inicijalizuj izlazni stream
            System.out.println(LOGIN);
            out.println(LOGIN);

            String status = (String) in.readObject();
            //System.out.println(status);

            if ((status != null) && status.contains("uspjesna!")) {

                stanje = true;//za svaki slučaj
                return "Prijavljeno!";
                /*notifyAll()/*System.out.println(status)*/
            }//Može neki print
            else if (status == null) {
                System.out.println("Neuspjesan login \n");

                return "Neuspjesan login!";//throw new IllegalAccessError();

            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        System.out.println("Greska pri unosu \n");

        return "Greska pri unosu!";
    }
    
    
    
    public void visible(){
        stanje=!stanje;
        
        String vid;
        if (stanje) {vid="0";}
        else {vid="1";}
        out.println("3:"+vid+":"+name);
        out.flush();
        System.out.println("3:"+vid+":"+name);
    }

    public void send() throws ClassNotFoundException {
        {
            if (stanje == true) {

                try {
                    out.println(SEND + ":" + name);
                    
                    LOGGEDIN = (ConcurrentHashMap<String, String>) in.readObject();
                    System.out.println(LOGGEDIN.toString());
                    {//Application.launch("./Controller/SEND.java");
                        
                    }
                    
                    //selekcija korisnika
                    return;/*
                    ObjectInputStream OIS = new ObjectInputStream(sockusr.getInputStream());
                    ObjectOutputStream OOS= new ObjectOutputStream (sockusr.getOutputStream());*/
                } catch (IOException ex) {
                    stanje=false;
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }


            } else {
                System.out.println("Niste ulogovani(vidljivi)!");
            }

        }
    }

}
