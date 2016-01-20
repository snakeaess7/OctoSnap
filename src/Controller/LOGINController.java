/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.sun.javafx.application.PlatformImpl;
import com.sun.org.apache.bcel.internal.classfile.Visitor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.print.DocFlavor;
import javafx.concurrent.Service;
import javafx.scene.image.WritableImage;


/**
 * FXML Controller class
 *
 * @author Delibor
 */
public class LOGINController implements Initializable {

    public static String succ = "Login";
    public static String name = "TEST";
    public String key = "123321";
    public String stat = "1";
    public static Client x;
    public static WritableImage SCR;
    public static BufferedImage SCRR;
    public static File SCRF;
    //public static LOGINController HACK;

    @FXML
    private Button LOGINbtn;
    @FXML
    private PasswordField PWD;
    @FXML
    private CheckBox VIS;
    @FXML
    private TextField NAME;
    @FXML
    private Label Label;
    @FXML
    private ImageView SNAP;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //HACK=this;
        System.out.println("Welcome!");
        Label.setText(succ);

        SNAP.setImage(new Image("file:SNAP.jpg"));
        //SCR = new Photo("file:SNAP.jpg");

        // TODO
    }

    @FXML
    private void log() throws InterruptedException, IOException, ClassNotFoundException {

        LOGINbtn.setDisable(true);
        name = NAME.getText();
        key = PWD.getText();
        if (VIS.isSelected()) {
            stat = "0";
        }

        //stat=new Boolean(VIS.isPressed()).toString();
        x = new Client(stat, key, name);
        x.setDaemon(true);

        x.start();

        //Thread.currentThread().sleep(2000);
        
        int seconds = 3;
        long t = System.currentTimeMillis()
        + seconds * 1000;
        while(System.currentTimeMillis() < t);
        

        if (succ.matches("Login")) {
            System.out.println("Timeout");
        }
        Label.setText(succ);
        System.out.println(succ);
        if (succ.matches("Prijavljeno!")) {
            if(stat.equals("1"))Client.stanje=false;
            
            try {
                replaceSceneContent("/octosnapinterface/FXMLDocumentFoldersAndAlbums.fxml");
            } catch (Exception ex) {
                Logger.getLogger(LOGINController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        LOGINbtn.setDisable(false);
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(OCTOSNAP.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = OCTOSNAP.stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            
            OCTOSNAP.stage.setScene(scene);
          
        } else {
            OCTOSNAP.stage.getScene().setRoot(page);
            
        }
        OCTOSNAP.stage.setTitle("OCTOSNAP!");
        OCTOSNAP.stage.sizeToScene();
        OCTOSNAP.stage.centerOnScreen();
        return page;
    }

}
