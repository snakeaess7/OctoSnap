/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 *
 * @author Delibor
 */
public class OCTOSNAP extends Application {

    public static Stage stage;
    private static File outputfile;
    public static OCTOSNAP HACK;

    @Override
    public void start(Stage staget) {
        HACK = this;
        stage = staget;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LOGIN.fxml"));

            Scene scene = new Scene(root);
            
            
            
               final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.F,

                                    KeyCombination.CONTROL_DOWN);
               
               
               
            

scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
    
                @Override
                public void handle(KeyEvent event) {

                    if (keyComb1.match(event)) {
                        
                          try {
                    Robot ro = new Robot();
                    Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                    int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                    BufferedImage im = ro.createScreenCapture(rect);
                    LOGINController.SCRR=im;
                    LOGINController.SCR= new WritableImage(width, height);
                    SwingFXUtils.toFXImage(LOGINController.SCRR, LOGINController.SCR);
                    outputfile = new File("Screenshot.jpg");
                    ImageIO.write(im, "jpg", outputfile);
                    outputfile.deleteOnExit();
                    
                  
                   
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                            
                        System.out.println("Screen captured");

                    }

                }

            });
            
            
            
            

            stage.setTitle("OCTOLOGIN");
            
            
           
            
            
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(OCTOSNAP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

    public void rec() {

        try {
            // FxmlLoader.loadDialog(this.getClass(), "IMGREC.fxml");
            Parent root = FXMLLoader.load(getClass().getResource("IMGREC.fxml"));

            Scene scene = new Scene(root);

         
            
            

            Stage stage = new Stage();

            stage.setTitle("INCOMING!");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(OCTOSNAP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
