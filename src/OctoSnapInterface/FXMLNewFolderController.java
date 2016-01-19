/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import static octosnapinterface.FXMLDocumentFoldersAndAlbumsController.currentFolder;
import static octosnapinterface.FXMLDocumentFoldersAndAlbumsController.prosiri;


/**
 * FXML Controller class
 *
 * @author Octo Muerto
 */
public class FXMLNewFolderController implements Initializable {

    @FXML
private TextField newFolderName;
    
public void cancel(ActionEvent event) {
    Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

}

public void createNewFile(ActionEvent event) {
    if (newFolderName.getText().length()>0) {
         Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        File newFolder = new File(FXMLDocumentFoldersAndAlbumsController.currentFolder,newFolderName.getText());
        newFolder.mkdir();
        stage.close();
    }
            
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
