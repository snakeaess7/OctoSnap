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
import java.util.HashMap;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    public static FXMLDocumentFoldersAndAlbumsController THIS;
    private Label label;
    @FXML
    private MenuItem menuItemOpen;
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
    public Label labelOnlineStatus;
    @FXML
    private ChoiceBox<Album> choiceBoxChosenAlbum;
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
    @FXML
    private Button btnCHANGESTATUS;
    @FXML
    private Button btnShowChart;
    
    //album promjenljive
    public static ObservableList<Album> albums = FXCollections.observableArrayList();
    private Album currentAlbum = null;
    public static int index = -1;

    //folderi promjenljive
    private int imageViewNumber = 0;
    private final int n = 8;
    ArrayList<File> fileList = new ArrayList();
    public static File currentFolder=null;
    File clipboardFolder=null;  //copyFile postavlja pokazivac na sta treba da se kopira, bilo fajl ili folder
    public static TreeItem<File> selectedTreeItem;
    
    //ostale promjenljive
    public static Photo selectedPhoto=null;
    public static Album destinationAlbum=null;
    private EnumType mode=EnumType.FOLDER;
    private EnumType selectionType;
    private ImageView selectedImageView;
    
    public static Scene originalScene;

    
    
    @FXML
    public void addToAlbum() {
        if (destinationAlbum != null) {
            index = albums.indexOf(destinationAlbum);
            Album a = albums.get(index);
            if (selectedPhoto != null) {
                a.addPhoto(selectedPhoto);
                a.save();
                albums.set(index, a);

            }

        }
    }
    
    public void deletePhotoFromAlbum() {
        index = albums.indexOf(currentAlbum);
        Album a = albums.get(index);
        if (selectedPhoto != null) {
            a.deletePhoto(selectedPhoto);
            a.save();
            albums.set(index, a);
            fileList.remove(new File(selectedPhoto.getUrl().substring(5, selectedPhoto.getUrl().length())));
            refresh();
        }

    }

    @FXML
    private void open() throws IOException {
        
        if(mode==EnumType.ALBUM){ 
        String name=selectedPhoto.getName();
        Integer currentvalue=(Integer)currentAlbum.count.get(name);
            
          currentAlbum.count.replace(name,(currentvalue + 1));
            System.out.println(currentAlbum.count);
            currentAlbum.save();
        }
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
            btnPaste.setDisable(false); menuItemPaste.setDisable(false);
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
            deletePhotoFromAlbum();
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
        try {
            Stage s = new Stage();
            Pane myPane;
            myPane = (Pane) FXMLLoader.load(getClass().getResource("/octosnapinterface/FXMLNewFolder.fxml"));
            Scene myScene = new Scene(myPane);
            s.setTitle("Make folder in "+currentFolder);
            s.setScene(myScene);
            s.setMinWidth(350);
            s.setMinHeight(176);
            s.show();
          
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            btnOpen.setDisable(true); menuItemOpen.setDisable(true);
            btnCopy.setDisable(false); menuItemCopy.setDisable(false);
            btnRename.setDisable(true); menuItemRename.setDisable(true);
            btnDelete.setDisable(false); menuItemDelete.setDisable(false);
            btnLoadNext.setDisable(false);
            btnLoadPrevious.setDisable(false);
            btnNewFolder.setDisable(false);
        }
        if(pastePosible()){
            btnPaste.setDisable(false); menuItemPaste.setDisable(false);
        }
        else {
            btnPaste.setDisable(true); menuItemPaste.setDisable(true);
        }
        
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
            btnOpen.setDisable(true); menuItemOpen.setDisable(true);
            btnCopy.setDisable(true); menuItemCopy.setDisable(true);
            btnPaste.setDisable(true); menuItemPaste.setDisable(true);
            btnRename.setDisable(false); menuItemRename.setDisable(false);
            btnDelete.setDisable(false); menuItemDelete.setDisable(false);
            btnLoadNext.setDisable(false);
            btnLoadPrevious.setDisable(false);
            btnShowChart.setDisable(false);
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
            btnOpen.setDisable(false); menuItemOpen.setDisable(false);
            btnCopy.setDisable(false); menuItemCopy.setDisable(false);
            btnPaste.setDisable(true); menuItemPaste.setDisable(true);
            btnRename.setDisable(true); menuItemRename.setDisable(true);
            btnDelete.setDisable(false); menuItemDelete.setDisable(false);
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
        btnOpen.setDisable(true); menuItemOpen.setDisable(true);
        btnCopy.setDisable(true); menuItemCopy.setDisable(true);
        btnPaste.setDisable(true); menuItemPaste.setDisable(true);
        btnRename.setDisable(true); menuItemRename.setDisable(true);
        btnDelete.setDisable(true); menuItemDelete.setDisable(true);
        btnLoadNext.setDisable(true);
        btnLoadPrevious.setDisable(true);
        btnNewFolder.setDisable(true);
        btnShowChart.setDisable(true);
        
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
        fileList = new ArrayList();
        ArrayList<String> photoArray= new ArrayList(a.getPhotos());
        for(String s:photoArray){
            String f=s.substring(5);
            fileList.add(new File(f));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        THIS=this;
        if (Controller.Client.stanje) labelOnlineStatus.setText("ONLINE"); else labelOnlineStatus.setText("OFFLINE");
        readAlbums();
        listView.setItems(albums);
        listView.setEditable(true);
        description.setEditable(false);
        
        choiceBoxChosenAlbum.setItems(albums);

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
        
        choiceBoxChosenAlbum.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
            @Override
            public void changed(ObservableValue<? extends Album> paramObservableValue, Album paramT1, Album selectedAlbum) {

                destinationAlbum=selectedAlbum;
                
                if(selectionType==EnumType.PHOTO)btnAddToAlbum.setDisable(false);

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
                if (selectedImageView!=null)selectedImageView.setBlendMode(BlendMode.SRC_OVER);
                selectedPhoto = (Photo) imageView.getImage();
                selectedImageView=imageView;
                selectedImageView.setBlendMode(BlendMode.COLOR_BURN);
                //photo selected actions:
                photoSelectedAction();
                
                
        }
            );
            
            imageView.setOnMouseEntered(mouseEvent -> {
                
                if(imageView.getBlendMode()!=BlendMode.COLOR_BURN)imageView.setBlendMode(BlendMode.DIFFERENCE);
                
            }
            );
            imageView.setOnMouseExited(mouseEvent -> {
                
                if(imageView.getBlendMode()!=BlendMode.COLOR_BURN)imageView.setBlendMode(BlendMode.SRC_OVER);
                
            }
            );
            
            
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
                btnPaste.setDisable(true); menuItemPaste.setDisable(true);
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
                    
                     /* if (a.count==null){a.count=new HashMap<>();}
                      if (a.count.isEmpty())
                      { for(String ph:a.getPhotos()){
                          a.count.put(ph, 0)
                                  }
                      a.save();
                                  
                                  }*/
                    
                    albums.add(a);
                }
                in.close();
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void CHANGESTATUS(ActionEvent event) {
        Controller.LOGINController.x.visible();
     if (Controller.Client.stanje) labelOnlineStatus.setText("ONLINE"); else labelOnlineStatus.setText("OFFLINE");
    
    }

    @FXML
    private void showHelp(ActionEvent event) {
        AnchorPane root = new AnchorPane();
          Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);
           Label help= new Label();
           help.autosize();
           help.setWrapText(true);
           help.setText("HELP: U Bilo kom momentu dok je program u fokusu pritisnuti ctrl+f za slikanje ekrana. Send screenshot traži od servera listu korisnika. Izaberite i pošaljite. \n Kad primate sliku, u slučaju spašavanja slike, sačuva se kao \"SS+broj spašenih slika od pokretanja programa\". Broj se resetuje pri svakom pokretanju i može doći do prepisavanja.");
           root.getChildren().add(help);
          stage.show();
        
        
    }

    @FXML
    private void showChart(ActionEvent event) {
        try {
            Stage s = new Stage();
            AnchorPane root = new AnchorPane();
            Scene myScene = new Scene(root);
            s.setTitle(currentAlbum.getName()+" views per photo chart");
            
            
            
            final NumberAxis xAxis = new NumberAxis();
            final CategoryAxis yAxis = new CategoryAxis();
            final BarChart<Number, String> bc = new BarChart<Number, String>(xAxis, yAxis);
            xAxis.setLabel("Views");
            xAxis.setTickLabelRotation(90);
            xAxis.setMinorTickVisible(false);
            xAxis.setTickUnit(2);
            yAxis.setLabel("Photos");

            
            //uraditi u for petlji!
            XYChart.Series series = new XYChart.Series();
            
            
             for (String each : currentAlbum.count.keySet()) {
                 String ime;
                 if(each.length()<16)
                     ime=each;
                 else
                     ime=each.substring(0, 16)+"...";
                series.getData().add(new XYChart.Data(currentAlbum.count.get(each),ime));
            }
            
            
            /*series.getData().add(new XYChart.Data(25601.34, "austria"));
            series.getData().add(new XYChart.Data(20148.82, "brazil"));
            series.getData().add(new XYChart.Data(10000, "france"));
            series.getData().add(new XYChart.Data(35407.15, "italy"));
            series.getData().add(new XYChart.Data(12000, "usa"));*/


            bc.getData().add(series);
            bc.setLegendVisible(false);
            bc.setHorizontalZeroLineVisible(false);
            
            root.getChildren().add(bc);
            
            s.setScene(myScene);
            s.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

enum EnumType {
    FOLDER, ALBUM, PHOTO
}
