/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import org.apache.commons.io.FileUtils;

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
    @FXML
    private Button btnNewFolder;

    //album promjenljive
    public static ObservableList<Album> albums = FXCollections.observableArrayList();
    private Album currentAlbum = null;
    public static int index = -1;

    //folderi promjenljive
    private int imageViewNumber = 0;
    private final int n = 8;
    ArrayList<File> fileList = new ArrayList();
    private File currentFolder=null;
    File clipboardFolder=null;  //copyFile postavlja pokazivac na sta treba da se kopira, bilo fajl ili folder
    private TreeItem<File> selectedTreeItem;
    
    //ostale promjenljive
    public static Photo selectedPhoto=null;
    public static Album destinationAlbum=null;
    private EnumType mode=EnumType.FOLDER;
    private EnumType selectionType;
    
    public static Scene originalScene;

    
    
    @FXML
    public void addToAlbum() {
        // TODO implement here
    }

    @FXML
    private void open() throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) btnOpen.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("FXMLDocumentPhotoView.fxml"));
        
        //save working scene
        originalScene=stage.getScene();
        double originalWidth=stage.getWidth();
        double originalHeight=stage.getHeight();

        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(originalWidth);
        stage.setHeight(originalHeight);
        
        stage.show();
    }

    @FXML
    public void copy() {
        if(selectionType==EnumType.FOLDER)
        {
            clipboardFolder = currentFolder;
            btnPaste.setDisable(false);
        }
        else
        {
            clipboardFolder = new File(selectedPhoto.getUrl().substring(5,selectedPhoto.getUrl().length()));
        }
        
    }

    @FXML
    public void paste() {
        try {
            pasteFile(selectedTreeItem);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void rename() {
        if(selectionType==EnumType.ALBUM)
        {
            renameAlbum();
        }
        else
        {
            //ne radimo za fajlove, samo za albume, samo ako ostane vremena
        }
        
    }

    @FXML
    public void delete() throws IOException {
        if(selectionType==EnumType.ALBUM)
        {
            deleteAlbum();
        }
        else if(selectionType==EnumType.FOLDER)
        {
            deleteFolder(selectedTreeItem);
        }
        else if(mode==EnumType.ALBUM)
        {
            //sanjina delete photo metoda
        }
        else
        {
            deletePhotoFromFolder();
        }
    }

    @FXML
    public void screenshot() {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Controller/IMGSEND.fxml"));
            
            
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            
            stage.setTitle("SEND!");
            
            
            
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentFoldersAndAlbumsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    public void createNewFolderButton(){
        // TODO implement here
    }
    
    
    
    
    
    
    
    @FXML
    public void setModeFOLDER(){
        //mode seting
        mode=EnumType.FOLDER;
        
        if(currentFolder==null)
            nothingSelectedAction();
        else
            folderSelectedAction();
    }
    
    @FXML
    public void setModeALBUM(){
        //mode seting
        mode=EnumType.ALBUM;
        
        if(currentAlbum==null)
            nothingSelectedAction();
        else
            albumSelectedAction();
    }
    
    
    
    
    private void folderSelectedAction(){
        //actions
        System.out.println(currentFolder.toPath()); // The newly selected TreeItem.
        tilepane.getChildren().clear();
        tilepane.setPrefHeight(200);
        imageViewNumber = 0;
        getListAndSize(currentFolder);
        showNextN();
        
        //only if previous selection wasn't of same type
        if (selectionType != EnumType.FOLDER) {

            //type seting
            selectionType = EnumType.FOLDER;
            
            //buttons seting
            btnAddToAlbum.setDisable(true);
            btnOpen.setDisable(true);
            btnCopy.setDisable(false);
            btnRename.setDisable(false);
            btnDelete.setDisable(false);
            btnLoadNext.setDisable(false);
            btnLoadPrevious.setDisable(false);
            btnNewFolder.setDisable(false);
        }
        if(pastePosible())btnPaste.setDisable(false);
        else btnPaste.setDisable(true);
        
        //labels seting
        labelSelectedObject.setText("Selected folder: " + currentFolder.getName());
        labelFullName.setText("Selected folder: " + currentFolder.getName());
    }
    
    private void albumSelectedAction(){
        //actions
        description.setText(currentAlbum.getDescription());
        tilepane.getChildren().clear();
        tilepane.setPrefHeight(200);
        imageViewNumber = 0;
        getListAndSize(currentAlbum);
        showNextN();
        
        //only if previous selection wasn't of same type
        if (selectionType != EnumType.ALBUM) {

            //type seting
            selectionType = EnumType.ALBUM;

            //buttons seting
            btnAddToAlbum.setDisable(true);
            btnOpen.setDisable(true);
            btnCopy.setDisable(true);
            btnPaste.setDisable(true);
            btnRename.setDisable(false);
            btnDelete.setDisable(false);
            btnLoadNext.setDisable(false);
            btnLoadPrevious.setDisable(false);
        }
        
        //labels seting
        labelSelectedObject.setText("Selected album: " + currentAlbum.getName());
        labelFullName.setText("Selected album: " + currentAlbum.getName());
    }
    
    private void photoSelectedAction(){
        //only if previous selection wasn't of same type
        if (selectionType != EnumType.PHOTO) {

            //type seting
            selectionType=EnumType.PHOTO;
            
            
            //buttons seting
            if (destinationAlbum != null) {
                btnAddToAlbum.setDisable(false);
            }
            btnOpen.setDisable(false);
            btnCopy.setDisable(false);
            btnPaste.setDisable(true);
            btnRename.setDisable(false);
            btnDelete.setDisable(false);
        }
        
        //labels seting
        labelSelectedObject.setText("Selected picture: " + selectedPhoto.getName());
        labelFullName.setText("Selected picture: " + selectedPhoto.getName());
    }
    
    private void nothingSelectedAction(){
        //actions
       tilepane.getChildren().clear();
        
        //type seting
        selectionType=null;
        
        //buttons seting
        
        btnAddToAlbum.setDisable(true);
        btnOpen.setDisable(true);
        btnCopy.setDisable(true);
        btnPaste.setDisable(true);
        btnRename.setDisable(true);
        btnDelete.setDisable(true);
        btnLoadNext.setDisable(true);
        btnLoadPrevious.setDisable(true);
        btnNewFolder.setDisable(true);
        
        //labels seting
        labelSelectedObject.setText("nothing selected");
        labelFullName.setText("nothing selected");
    }
    
    
    
    
    
    
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

                currentFolder = new File(selectedItem.getValue().toString());
                selectedTreeItem=selectedItem;
                
                folderSelectedAction();

            }
        });
        btnChangeDrive.fire();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
            @Override
            public void changed(ObservableValue<? extends Album> paramObservableValue, Album paramT1, Album selectedAlbum) {

                currentAlbum = selectedAlbum;
                
                albumSelectedAction();

            }
        });
        
        nothingSelectedAction();
    }

    @FXML
    private void showTree(ActionEvent event) {
        TreeItem<File> rootItem = new TreeItem<>(new File(drive.getValue().toString()));
        prosiri(rootItem);
        tree.setRoot(rootItem);
        nothingSelectedAction();

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
                selectedPhoto = (Photo) imageView.getImage();
                
                //photo selected actions:
                photoSelectedAction();
                
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
    
    public void pasteFile(TreeItem<File> whereToCopy) throws IOException { //daj metodi odabrani TreeItem.
        File whereToCopyFolder = new File(whereToCopy.getValue().toString());
        if (clipboardFolder != null) {
            if (clipboardFolder.isDirectory()) { //ako kopiras folder, onda trebas osvjeziti treeView, pa zato koristim TreeItem
                FileUtils.copyDirectoryToDirectory(clipboardFolder, whereToCopyFolder);
                TreeItem<File> pastedTreeItem = new TreeItem<File>(clipboardFolder);
                whereToCopy.getChildren().add(pastedTreeItem);
                prosiri(pastedTreeItem);
            } else {
                FileUtils.copyFileToDirectory(clipboardFolder, whereToCopyFolder); //ako je fajl, samo ga nalijepis u odabrani folder
                fileList.add(clipboardFolder);
            }
        }
        refresh();
    }
    
    private boolean pastePosible(){
        if(clipboardFolder == null)return false;
        
        for (File each : currentFolder.listFiles()) {
            if (each.getName().equals(clipboardFolder.getName())) {
                return false;
            }
        }
        
        return true;
    }
    
    public void deleteFolder(TreeItem<File> treeItemToDelete) throws IOException {
        File folderToDelete = new File(treeItemToDelete.getValue().toString());
        FileUtils.deleteDirectory(folderToDelete);
        treeItemToDelete.getParent().getChildren().remove(treeItemToDelete);
    }
    
    public void deletePhotoFromFolder() {
        Photo photoToDelete = selectedPhoto;
        File fileToDelete = new File(photoToDelete.getUrl().substring(5, photoToDelete.getUrl().length()));
        fileToDelete.delete();
        
        fileList.remove(fileToDelete);
        refresh();
        selectedPhoto=null;
        nothingSelectedAction();
    }
    
    private void refresh(){
        imageViewNumber -= 8;
        if (imageViewNumber < 0) {
            imageViewNumber = 0;
        }
        showNextN();
    }

    void renameAlbum() {
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

    void deleteAlbum() {
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

enum EnumType {
    FOLDER, ALBUM, PHOTO
}