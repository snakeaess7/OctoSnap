/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author korisnik
 */
public class FXMLDocumentPhotoViewController implements Initializable {

    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private MenuItem menuItemSave;
    @FXML
    private MenuItem menuItemQuit;
    @FXML
    private MenuItem menuItemCopy;
    @FXML
    private MenuItem menuItemPaste;
    @FXML
    private MenuItem menuItemRename;
    @FXML
    private MenuItem menuItemDelete;
    @FXML
    private MenuItem menuItemInfo;
    @FXML
    private Button btnSendScreenshot;
    @FXML
    private Label labelOnlineStatus;
    @FXML
    private ChoiceBox<?> choiceBoxChosenAlbum;
    @FXML
    private Button btnAddToAlbum;
    @FXML
    private Button btnRotateLeft;
    @FXML
    private Button btnRotateRight;
    @FXML
    private Button btnFullscreen;
    @FXML
    private Button btnClosePicture;
    @FXML
    private Label labelStatusBar;
    @FXML
    private Color x4;
    @FXML
    private Font x3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
