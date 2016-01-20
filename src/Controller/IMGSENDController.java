/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static Controller.Client.IP;
import static Controller.Client.PORT;
import static Controller.Client.LOGGEDIN;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Delibor
 */
public class IMGSENDController implements Initializable {

    @FXML
    private ListView<String> UserList;
    @FXML
    private Button SendBtn;
    @FXML
    private ImageView ScrSht;
    @FXML
    private Label TEXT;

    @FXML
    private void send() {
        if ((new File("Screenshot.jpg").exists() | (octosnapinterface.FXMLDocumentPhotoViewController.SNDIMG))) {
            try {
                //System.out.println(UserList.getSelectionModel().getSelectedItem());
                String IPSEND = LOGGEDIN.get(UserList.getSelectionModel().getSelectedItem()).replace("/", "");
                System.out.println("Šaljem na:" + IPSEND);

                Socket sockusr = new Socket(IPSEND, PORT + 1);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                sockusr.getInputStream()));

                ObjectOutputStream out = new ObjectOutputStream(
                        sockusr.getOutputStream());
                //System.out.println(LOGINController.name);
                out.writeObject(LOGINController.name);

                DataInputStream in2;
                if ((octosnapinterface.FXMLDocumentPhotoViewController.SNDIMG)) {
                    System.out.println(octosnapinterface.FXMLDocumentFoldersAndAlbumsController.selectedPhoto.getUrl().substring(5));
                    in2 = new DataInputStream(new FileInputStream(octosnapinterface.FXMLDocumentFoldersAndAlbumsController.selectedPhoto.getUrl().substring(5)));
                    
                } else {
                    in2 = new DataInputStream(new FileInputStream("Screenshot.jpg"));
                }
                copyStream(in2, new DataOutputStream(sockusr.getOutputStream()));
                in2.close();
                sockusr.getOutputStream().close();


                /*
            int count;
            byte[] buffer = new byte[1024];

            OutputStream out2 = sockusr.getOutputStream();
            BufferedInputStream in2 = new BufferedInputStream( new FileInputStream("Screenshot.jpg"));
            while ((count = in2.read(buffer)) > 0) {
                out.write(buffer, 0, count);
                out.flush();
            }
            in2.close();*/
                //out2.close();
                //  ImageIO.write(LOGINController.SCRR, "JPG", out);
                /* ByteArrayOutputStream bScrn = new ByteArrayOutputStream();
            ImageIO.write(LOGINController.SCRR, "JPG", bScrn);
            byte imgBytes[] = bScrn.toByteArray();

            out.write((Integer.toString(imgBytes.length)).getBytes());
            out.write(imgBytes, 0, imgBytes.length);*/

 /*int seconds = 7;
        long t = System.currentTimeMillis()
        + seconds * 1000;
        while(System.currentTimeMillis() < t);/*
        
        
            if (in.ready()) {
                String odg = in.readLine();
                System.out.println("2");
                if (odg.equalsIgnoreCase("YES")) {
                    out.writeObject(LOGINController.SCR);
                    TEXT.setText("SLIKA POSLANA");
                } else {
                    TEXT.setText("KORISNIK JE ODBIO SLIKU");
                }
            }
            else TEXT.setText("Timeout");*/
               if (octosnapinterface.FXMLDocumentPhotoViewController.SNDIMG) octosnapinterface.FXMLDocumentPhotoViewController.SNDIMG=!(octosnapinterface.FXMLDocumentPhotoViewController.SNDIMG);
 
                System.out.println("SENT");
                
                
                
            } catch (IOException ex) {
                TEXT.setText("KORISNIK SE DISKONEKTOVAO");
            }
        } else {
            TEXT.setText("Slika ne postoji");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if ((octosnapinterface.FXMLDocumentPhotoViewController.SNDIMG)) {
                ScrSht.setImage(octosnapinterface.FXMLDocumentFoldersAndAlbumsController.selectedPhoto);
            } else {

                ScrSht.setImage(LOGINController.SCR);
            }
            if (LOGINController.x != null) {
                LOGINController.x.send();
                if (LOGINController.x.stanje == false) {
                    TEXT.setText("INVISIBLE");
                    LOGGEDIN.clear();
                }
            } else {
                TEXT.setText("NOT LOGGED IN");
            }
            if (LOGGEDIN != null) {
                UserList.getItems().addAll(Collections.list(LOGGEDIN.keys()));
            }

            // UserList.getItems().addAll(serverThread.serverThread.ASKEY.values()); //To change body of generated methods, choose Tools | Templates.
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IMGSENDController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void copyStream(DataInputStream input, DataOutputStream output)
            throws IOException {
        // Može baferovano, ali za svaki slučaj bajt po bajt.
        //int bytesRead;

        int i;
        while ((i = input.read()) != -1) {

            output.write(i);
            output.flush();
        }

        //System.out.println("Controller.IMGSENDController.copyStream()");
    }

}
