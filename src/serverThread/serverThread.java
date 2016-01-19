/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverThread;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Delibor
 */
public class serverThread extends Thread {

    public static ConcurrentHashMap<String, String> ASKEY;
    public static ConcurrentHashMap<String, String> LOGGEDIN;

    static {
        File AK = new File("AK");

        File LI = new File("LI");
        try {
            if (AK.exists()) {
                ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream(AK));
                ASKEY = (ConcurrentHashMap<String, String>) ois1.readObject();
                ois1.close();
            } else {
                ASKEY = new ConcurrentHashMap<String, String>();
            }
            /*if (LI.exists()) {
                ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(AK));
                LOGGEDIN = (ConcurrentHashMap<String, String>) ois2.readObject();
                ois2.close();
            }   else */
            LOGGEDIN = new ConcurrentHashMap<String, String>();
        } catch (IOException ex) {
            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected Socket s;
    protected String request;

    BufferedReader in
            = null;
    ObjectOutputStream out = null;

    public serverThread(Socket s) throws IOException {

        this.s = s;
        request = null;
        in = new BufferedReader(
                new InputStreamReader(
                        s.getInputStream()));
        // inicijalizuj izlazni stream

        out
                = new ObjectOutputStream(
                        s.getOutputStream());

        start();

    }

    @Override
    public void run() {
        String name = null;

        try {
            // super.run(); //To change body of generated methods, choose Tools | Templates.
            // inicijalizuj ulazni stream

            for (;;) {
                sleep(1000);
               
               
                request = in.readLine();
                
                if (request != null) {//onako
                    System.out.println(request);

                    System.out.println(LOGGEDIN);;

                    String req[] = request.split(":");

                    if (req[0].matches("0")) {
                        name = new String(req[2]);
                        System.out.println("Vidljiva prijava korisnika " + name + "\n");
                        String msg = login(req);
                        System.out.println(msg + "\n");
                        LOGGEDIN.put(req[2], s.getInetAddress().toString());

                        out.writeObject(msg);

                    } else if (req[0].matches("1")) {
                        name = new String(req[2]);
                        System.out.println("Nevidljiva prijava korisnika " + name + "\n");
                        String msg = login(req);
                        System.out.println(msg + "\n");
                        out.writeObject(msg);

                    } else if (req[0].matches("2")) {
                        System.out.println("Zahtjev za slanje korisnika " + req[1] + "\n");
                        out.writeObject(transfer());
                        //wait();//Ne znam riješiti

                        finalize();//možda?
                       
                       

                    }else if (req[0].matches("3")) {
                        if (req[1].equals("0")) {
                        LOGGEDIN.put(req[2], s.getInetAddress().toString());
                        System.out.println("Korisnik "+req[2]+"je postao vidljiv \n");}
                        else if (req[1].equals("1")){
                            LOGGEDIN.remove(req[2]);
                            System.out.println("Korisnik "+req[2]+" je postao nevidljiv \n");
                            
                        }
                        
                        
                        
                    }
                
                
                }
                
                

            }
            //System.out.println(LOGGEDIN.toString());

        } catch (IOException ex) {

            LOGGEDIN.remove(name);
            System.out.println("diskonekt, preostali " + LOGGEDIN);
            //Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
           
            return;

        } catch (InterruptedException ex) {
            System.out.println("interrupted! \n");
            

            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
            
            return;
        } catch (Throwable ex) {
            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
            try {
                out.writeObject("QUERY???");
            } catch (IOException ex1) {
                Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return;
        } finally {

            // in.close();
            //out.close();
        }

    }

    private String login(String[] req) throws FileNotFoundException {
        //System.out.println("serverThread.serverThread.login() " + req[1]);
        if (ASKEY.containsKey(req[1])) {
            if (ASKEY.containsValue(req[2])) {
                return "Prijava uspjesna!";
            } else {
                return "Ime se ne podudara, registrovano ime je " + ASKEY.get(req[1]); //HACK ako username sadrži uspjesna!
            }
        }
        File keys = new File("keys.txt");

        try {
            keys.createNewFile();
            BufferedReader brd = new BufferedReader(new FileReader(keys));
            for (String key = brd.readLine(); key != null;key=brd.readLine()) {
                //System.out.println(key);
                if (key.equals(req[1])) {
                    ASKEY.put(req[1], req[2]);
                    Object lock1 = new Object();
                    System.out.println("Novi korisnik!");
                    synchronized (lock1) {
                        try (ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("AK"))) {

                            oos1.writeObject(ASKEY);
                        }

                        System.out.println("Korisnik " + req[2] + " dodan \n");

                    }

                    return "Registracija uspjesna!";
                }
                
            }

            brd.close();
        } catch (IOException ex) {
            return "greska u fajlu sa kljucevima";
            //Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Neuspjesno!";

    }

    private ConcurrentHashMap<String, String> transfer() {

        return LOGGEDIN;

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        //To change body of generated methods, choose Tools | Templates.
    }

}
