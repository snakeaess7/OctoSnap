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

    public serverThread(Socket s) {
        this.s = s;
        start();
        request = null;
    }

    @Override
    public void run() {

        BufferedReader in
                = null;
        PrintWriter out = null;
        try {
            // super.run(); //To change body of generated methods, choose Tools | Templates.
            // inicijalizuj ulazni stream
            in = new BufferedReader(
                    new InputStreamReader(
                            s.getInputStream()));
            // inicijalizuj izlazni stream

            out
                    = new PrintWriter(
                            new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

            for (;;) {
                if (in.ready()){//wasted processing power
                        request = in.readLine();
                if(request==null) wait (1000);

            String req[] = request.split(":");
            if (req[0].matches("0")) {
                String msg = login(req);
                LOGGEDIN.put(req[2], s.getInetAddress().toString());
                System.out.println(msg + "\n");
                out.println(msg);
            } else if (req[0].matches("1")) {
                String msg = login(req);
                System.out.println(msg + "\n");
                out.println(msg);
            } else if (req[0].matches("2")) {
                ObjectOutputStream OOS = new ObjectOutputStream(s.getOutputStream());
                OOS.writeObject(transfer());
                OOS.close();
            }
            }
            //System.out.println(LOGGEDIN.toString());
             
        } 
            
        } catch (IOException ex) {
            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
                            System.out.println("interrupted! \n");

            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private String login(String[] req) throws FileNotFoundException {
        System.out.println("serverThread.serverThread.login() " + req[1] + "\n");
        if (ASKEY.containsKey(req[1])) {
            if (ASKEY.containsValue(req[2])) {
                return "Prijava uspjesna!";
            } else {
                return "Ime se ne podudara, registrovano ime je " + ASKEY.get(req[1]).toString(); //HACK ako username sadrži uspjesna!
            }
        }
        File keys = new File("keys.txt");

        try {
            keys.createNewFile();
            BufferedReader brd = new BufferedReader(new FileReader(keys));
            for (String key = brd.readLine(); key != null;) {
                //System.out.println(key);
                if (key.equals(req[1])) {
                    ASKEY.put(req[1], req[2]);
                    Object lock1 = new Object();

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

}
