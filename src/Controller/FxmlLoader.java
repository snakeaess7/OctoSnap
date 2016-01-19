/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Igor
 */
public class FxmlLoader {

    public static void load(Class c, String fxml) {
        try {
            Stage s = new Stage();
            Pane myPane = (Pane) FXMLLoader.load(c.getResource(fxml));
            Scene myScene = new Scene(myPane);
            s.setScene(myScene);
            s.show();
        } catch (IOException ex) {
            Logger.getLogger(c.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadDialog(Class c, String fxml) {
        try {
            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            Pane myPane = (Pane) FXMLLoader.load(c.getResource(fxml));
            Scene myScene = new Scene(myPane);
            s.setScene(myScene);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(c.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
