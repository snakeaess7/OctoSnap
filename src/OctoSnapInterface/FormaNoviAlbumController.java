/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import static octosnapinterface.FXMLDocumentFoldersAndAlbumsController.albums;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sanja
 */
public class FormaNoviAlbumController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameTF;

    @FXML
    private TextArea descriptionTA;
    
    @FXML
    private Button clearButton;

    @FXML
    private Button discardButton;

    @FXML
    private Button saveButton;

    private String name;
    private String description;

    @FXML
    void save(ActionEvent event) {
        name = nameTF.getText();
        description = descriptionTA.getText();
        Album newAlbum = null;
        if ((newAlbum = new Album(name, description)) != null) {
            if (newAlbum.save()) {
                albums.add(newAlbum);
            }
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void discard(ActionEvent event) {
        nameTF.clear();
        descriptionTA.clear();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    void clear(ActionEvent event) {
        nameTF.clear();
        descriptionTA.clear();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

}
