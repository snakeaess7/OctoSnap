/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author korisnik
 */
public class FXMLDocumentFoldersAndAlbumsController implements Initializable {

    private Label label;
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
    private Label labelSelectedObject;
    @FXML
    private Button btnOpen;
    @FXML
    private Button btnCopy;
    @FXML
    private Button btnRename;
    @FXML
    private Button btnPaste;
    @FXML
    private Button btnDelete;
    @FXML
    private ChoiceBox drive;
    @FXML
    private TreeView<File> tree;
    @FXML
    private TilePane tilepane;
    @FXML
    private Button btnChangeDrive;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Button btnLoadPrevious;
    @FXML
    private Button btnLoadNext;
    @FXML
    private TextArea description;
    @FXML
    private ListView<Album> listView;
    @FXML
    private Label labelFullName;

    //album promjenljive
    public static ObservableList<Album> albums = FXCollections.observableArrayList();
    private Album currentAlbum = null;
    public static int index = -1;

    //folderi promjenljive
    private int imageViewNumber = 0;
    private int n = 8;
    ArrayList<File> fileList = new ArrayList();

    public void getListAndSize(File folder) {
        fileList = new ArrayList();
        for (File each : folder.listFiles()) {
            if (each.getName().endsWith(".png") || each.getName().endsWith(".bmp") || each.getName().endsWith(".jpg")) {
                fileList.add(each);
            }
        }
    }

    public void getListAndSize(Album a) {
        fileList = new ArrayList(a.getPhotos());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readAlbums();
        listView.setItems(albums);
        listView.setEditable(true);
        description.setEditable(false);

        tilepane.setPrefTileHeight(200);
        tilepane.setPrefTileWidth(200);
        tilepane.setHgap(20);
        tilepane.setVgap(20);
        tilepane.setPrefColumns(10);
        File[] roots = File.listRoots();
        for (File each : roots) {
            drive.getItems().add(each.toString());
        }
        drive.getSelectionModel().selectFirst();
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem> paramObservableValue, TreeItem paramT1, TreeItem selectedItem) {

                File currentFolder = new File(selectedItem.getValue().toString());
                System.out.println(currentFolder.toPath()); // The newly selected TreeItem.
                tilepane.getChildren().clear();
                tilepane.setPrefHeight(200);
                imageViewNumber = 0;
                getListAndSize(currentFolder);
                showNextN();

            }
        });
        btnChangeDrive.fire();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
            @Override
            public void changed(ObservableValue<? extends Album> paramObservableValue, Album paramT1, Album selectedAlbum) {

                currentAlbum = selectedAlbum;
                description.setText(currentAlbum.getDescription());
                tilepane.getChildren().clear();
                tilepane.setPrefHeight(200);
                imageViewNumber = 0;
                getListAndSize(currentAlbum);
                showNextN();

            }
        });
    }

    @FXML
    private void showTree(ActionEvent event) {
        TreeItem<File> rootItem = new TreeItem<>(new File(drive.getValue().toString()));
        prosiri(rootItem);
        tree.setRoot(rootItem);

    }

    @FXML
    public void showNextN() {
        tilepane.setPrefHeight(scrollpane.getHeight());
        tilepane.setPrefWidth(scrollpane.getWidth());
        int height = (int) (scrollpane.getHeight() - 60) / 2;
        int width = (int) (scrollpane.getWidth() - 100) / 4;
        int end = imageViewNumber + n;
        if (end > fileList.size()) {
            end = fileList.size();
        }
        tilepane.getChildren().clear();
        for (int i = imageViewNumber; i < end; i++) {
            ImageView imageView = new ImageView(new Photo("file:" + fileList.get(i).getPath()));
            imageView.setPreserveRatio(true);
            tilepane.setPrefTileHeight(height);
            tilepane.setPrefTileWidth(width);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            tilepane.getChildren().add(imageView);
            //akcija pri selekciji slike
            imageView.setOnMouseClicked(mouseEvent -> {
                Photo photo = (Photo) imageView.getImage();
                labelSelectedObject.setText("Selected picture: " + photo.getName());
                labelFullName.setText("Selected picture: " + photo.getName());
            });
        }
        if (end < fileList.size()) {
            imageViewNumber += n;
        }
    }

    @FXML
    public void showPrevN() {
        imageViewNumber -= 2 * n;
        if (imageViewNumber < 0) {
            imageViewNumber = 0;
        }
        showNextN();
    }

    static void prosiri(TreeItem<File> treeItem) {
        for (File each : treeItem.getValue().listFiles()) {
            if (each.isDirectory() && !each.isHidden() && each.canRead() && (Files.isReadable(each.toPath()) && !Files.isSymbolicLink(each.toPath()))) {
                TreeItem<File> newTreeItem = new TreeItem<File>(each);
                treeItem.getChildren().add(newTreeItem);
            }
        }
        treeItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //System.out.println("newValue = " + newValue);
                BooleanProperty bb = (BooleanProperty) observable;
                //System.out.println("bb.getBean() = " + bb.getBean());
                TreeItem t = (TreeItem) bb.getBean();
                treeItem.expandedProperty().removeListener(this);
                for (TreeItem<File> each : treeItem.getChildren()) {
                    prosiri(each);
                }

            }
        });
    }

    @FXML
    void renameAlbumButton(ActionEvent event) {
        index = listView.getSelectionModel().getSelectedIndex();

        try {
            Stage s = new Stage();
            Pane myPane2;
            myPane2 = (Pane) FXMLLoader.load(getClass().getResource("/octosnapinterface/FormaRename.fxml"));
            Scene myScene = new Scene(myPane2);
            s.setTitle("Rename Album");
            s.setScene(myScene);
            s.setAlwaysOnTop(true);
            s.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void createNewAlbumButton(ActionEvent event) {

        try {
            Stage s = new Stage();
            Pane myPane;
            myPane = (Pane) FXMLLoader.load(getClass().getResource("/octosnapinterface/FormaNoviAlbum.fxml"));
            Scene myScene = new Scene(myPane);
            s.setTitle("New Album");
            s.setScene(myScene);
            s.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void deleteAlbumButton(ActionEvent event) {
        int index = listView.getSelectionModel().getSelectedIndex();
        java.io.File f = albums.get(index).search();
        if (f.delete()) {
            albums.remove(index);
        }
        description.clear();
    }

    public static void readAlbums() {
        albums.clear();
        java.io.File dir = new java.io.File("." + java.io.File.separator + "albums");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        java.io.File[] files = dir.listFiles(new AlbumFilter());
        ObjectInputStream in = null;
        for (java.io.File f : files) {
            try {
                in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
                Album a = null;
                a = (Album) in.readObject();
                if (a != null) {
                    albums.add(a);
                }
                in.close();
            } catch (Exception e) {
            }
        }
    }

}
