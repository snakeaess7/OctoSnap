/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeview;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
/**
 *
 * @author Octo Muerto
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button button;
    @FXML
    private TreeView<File> tree;
    @FXML
    private ChoiceBox drive;
    @FXML
    private TilePane tilepane;
    
    @FXML
    private void showTree(ActionEvent event) {
        TreeItem<File> rootItem = new TreeItem<>(new File(drive.getValue().toString()));
        prosiri(rootItem);
        tree.setRoot(rootItem);
        
    }
    
        private int imageViewNumber=0;
        private int n=20;
        ArrayList<File> fileList = new ArrayList();
    public void getListAndSize(File folder) {
        fileList = new ArrayList();
        for (File each : folder.listFiles())
    if (each.getName().endsWith(".png") || each.getName().endsWith(".bmp") || each.getName().endsWith(".jpg"))
        fileList.add(each);
    }
    public void showNextN() {
    int end=imageViewNumber+n;
    if (end>fileList.size())
        end=fileList.size();
    tilepane.getChildren().clear();
    for(int i=imageViewNumber;i<end;i++) {
                  ImageView imageView = new ImageView(new Photo("file:"+fileList.get(i).getPath()));
                  imageView.setPreserveRatio(true);
                  imageView.setFitWidth(100);
                  imageView.setFitHeight(100);
                  tilepane.getChildren().add(imageView);
imageView.setOnMouseClicked(mouseEvent -> {
                                                Photo photo = (Photo) imageView.getImage();
                                                System.out.println(photo.getUrl().substring(5));
                                                        });
    }
    if (end<fileList.size())
    imageViewNumber+=n;
    }
    
    public  void showPrevN() {
    imageViewNumber-=2*n;
    if(imageViewNumber<0)
        imageViewNumber=0;
    showNextN();
    }
    static void prosiri(TreeItem<File> treeItem) {
    for (File each : treeItem.getValue().listFiles())
        if (each.isDirectory() && !each.isHidden() && each.canRead() && (Files.isReadable(each.toPath()) && !Files.isSymbolicLink(each.toPath()))) {
            TreeItem<File> newTreeItem = new TreeItem<File> (each);
            treeItem.getChildren().add(newTreeItem);
            }
            treeItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        //System.out.println("newValue = " + newValue);
        BooleanProperty bb = (BooleanProperty) observable;
        //System.out.println("bb.getBean() = " + bb.getBean());
        TreeItem t = (TreeItem) bb.getBean();
        treeItem.expandedProperty().removeListener(this);
        for (TreeItem<File> each : treeItem.getChildren())
            prosiri(each);

            
    }
});
        }
   
        
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tilepane.setPrefTileHeight(100);
        tilepane.setPrefTileWidth(100);
        tilepane.setHgap(20);
        tilepane.setVgap(20);
        tilepane.setPrefRows(2);
        File[] roots = File.listRoots();
        for(File each : roots)
    drive.getItems().add(each.toString());
            drive.getSelectionModel().selectFirst();
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>() {
     @Override
     public void changed( ObservableValue<? extends TreeItem> paramObservableValue, TreeItem paramT1, TreeItem selectedItem) {
                    
                    File currentFolder = new File(selectedItem.getValue().toString());
                            System.out.println(currentFolder.toPath()); // The newly selected TreeItem.
                                tilepane.getChildren().clear();            
                            tilepane.setPrefHeight(200);
                  imageViewNumber=0;
                  getListAndSize(currentFolder);
                  showNextN();
              
     }
});
    }    
    
}


