/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sanja
 */
public class FormaNoviAlbumControllerTest {
    
    public FormaNoviAlbumControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of save method, of class FormaNoviAlbumController.
     */
    @Test(expected = NullPointerException.class)
    public void testSave() {
        System.out.println("save");
        ActionEvent event = null;
        FormaNoviAlbumController instance = new FormaNoviAlbumController();
        instance.save(event);
    }

    /**
     * Test of discard method, of class FormaNoviAlbumController.
     */
    @Test(expected = NullPointerException.class)
    public void testDiscard() {
        System.out.println("discard");
        ActionEvent event = null;
        FormaNoviAlbumController instance = new FormaNoviAlbumController();
        instance.discard(event);
    }

    /**
     * Test of clear method, of class FormaNoviAlbumController.
     */
    @Test(expected = NullPointerException.class)
    public void testClear() {
        System.out.println("clear");
        ActionEvent event = null;
        FormaNoviAlbumController instance = new FormaNoviAlbumController();
        instance.clear(event);
    }

    /**
     * Test of initialize method, of class FormaNoviAlbumController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FormaNoviAlbumController instance = new FormaNoviAlbumController();
        instance.initialize(url, rb);
    }
    
}
