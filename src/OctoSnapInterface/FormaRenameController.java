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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static octosnapinterface.FXMLDocumentFoldersAndAlbumsController.albums;
import static octosnapinterface.FXMLDocumentFoldersAndAlbumsController.index;

/**
 * FXML Controller class
 *
 * @author Sanja
 */
public class FormaRenameController implements Initializable {

    @FXML
    private Button dontSaveChangesButton;

    @FXML
    private TextField oldNameTF;

    @FXML
    private TextField newNameTF;

    @FXML
    private Button saveChangesButton;

    @FXML
    void saveChanges(ActionEvent event) {
        try {
            String newName = newNameTF.getText();
            Album a=albums.get(index);
            a.rename(newName);
            a.save();
            albums.set(index, a);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
        }

    }

    @FXML
    void dontSaveChanges(ActionEvent event) {
        newNameTF.clear();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oldNameTF.setDisable(true);
        oldNameTF.setText(FXMLDocumentFoldersAndAlbumsController.albums.
                get(FXMLDocumentFoldersAndAlbumsController.index).toString());
    }

}
