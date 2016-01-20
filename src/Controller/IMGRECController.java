/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javax.print.DocFlavor;

/**
 * FXML Controller class
 *
 * @author Delibor
 */
public class IMGRECController implements Initializable {

    @FXML
    private Button btnNO;
    @FXML
    private Button btnYES;
    @FXML
    private ImageView IMAGE;
    private ObjectInputStream in;
    private PrintWriter out;
    private File JPG;
  
    private static int cnt;
    @FXML
    private Label name;
    @FXML
    private Button save;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            in
                    = new ObjectInputStream(
                            Client.sockusr.getInputStream());
            out
                    = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            Client.sockusr.getOutputStream())), true);
          String ink="NOT DETECTED";//onako
          ink=(String) in.readObject();
            System.out.println(ink);
            name.setText(ink);

            //  p=ImageIO.read(ImageIO.createImageInputStream(in));
           File rec=new File("SSREC.jpg");
            FileOutputStream fos = new FileOutputStream(rec);
            DataOutputStream fout = new DataOutputStream(fos);
            rec.deleteOnExit();
           
        
           
            
           IMGSENDController.copyStream( new DataInputStream(Client.sockusr.getInputStream()), fout);
           
           fout.close();
           System.out.println("1");
            
        
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(IMGRECController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void NO(ActionEvent event) {
        
        out.write("NO");
        new File("SSREC.jpg").delete();
    }

    @FXML
    private void YES(ActionEvent event) throws IOException {

        out.write("YES");

        
        IMAGE.autosize();
        IMAGE.setImage(new Image("file:SSREC.jpg"));
       
         
         btnYES.setDisable(true);
         save.setDisable(false);

    }

    @FXML
    private void save(ActionEvent event) throws FileNotFoundException, IOException {
        FileOutputStream fo=new FileOutputStream("SS"+cnt+++".jpg", false);
        FileInputStream fi = new FileInputStream("SSREC.jpg");
        int i=0;
        while ((i = fi.read()) != -1)fo.write(i);
        fo.close();
        fi.close();
        save.setDisable(true);
    }
    
}
