/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    private Label labelFullName;
    @FXML
    private ScrollPane scrollPaneMainArea;
    @FXML
    private TilePane tilePaneMainArea;

    private ImageView imageViewMainArea;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imageViewMainArea = new ImageView(FXMLDocumentFoldersAndAlbumsController.selectedPhoto);
        imageViewMainArea.setPreserveRatio(true);
        imageViewMainArea.setSmooth(true);

        scrollPaneMainArea.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                double w1 = newValue.getWidth();
                double h1 = newValue.getHeight();
                imageViewMainArea.setFitWidth(w1);
                imageViewMainArea.setFitHeight(h1);
                double w2 = imageViewMainArea.getBoundsInParent().getWidth();
                double h2 = imageViewMainArea.getBoundsInParent().getHeight();
                if (w1 != w2) {
                    double leftInset = (w1 - w2) / 2;
                    tilePaneMainArea.setPadding(new Insets(0, 0, 0, leftInset));
                }
                if (h1 != h2) {
                    double topInset = (h1 - h2) / 2;
                    tilePaneMainArea.setPadding(new Insets(topInset, 0, 0, 0));
                }
            }
        });

        tilePaneMainArea.getChildren().add(imageViewMainArea);

    }

    @FXML
    private void rotateLeft(ActionEvent event) {
        imageViewMainArea.setRotate(imageViewMainArea.getRotate() - 90);
    }

    @FXML
    private void rotateRight(ActionEvent event) {
        imageViewMainArea.setRotate(imageViewMainArea.getRotate() + 90);
    }

    @FXML
    private void fullscreen(ActionEvent event) {
        Stage stage = new Stage();
        stage.setFullScreen(true);

        ImageView imageV = new ImageView(FXMLDocumentFoldersAndAlbumsController.selectedPhoto);
        //stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCodeCombination.NO_MATCH);

        AnchorPane root = new AnchorPane();
        
        imageV.fitWidthProperty().bind(stage.widthProperty());//fit to screen
        imageV.fitHeightProperty().bind(stage.heightProperty());//fit
        //imageV.setPreserveRatio(true);

        root.getChildren().add(imageV);

        //imageV.setLayoutX(root.getWidth() / 2- imageV.getBoundsInParent());
        //imageV.setLayoutY(root.getHeight() / 2- imageV.getBoundsInParent().get);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ESCAPE) {

                    stage.close();

                    //Stop letting it do anything else
                    keyEvent.consume();

                }

            }

        });

        stage.setScene(scene);
        //stage.centerOnScreen();
        imageV.autosize();

        stage.show();
    }

    @FXML
    private void closePicture(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage) btnClosePicture.getScene().getWindow();

        //create a new scene with root and set the stage
        Scene scene = FXMLDocumentFoldersAndAlbumsController.originalScene;
        stage.setScene(scene);
        stage.show();
    }

}
