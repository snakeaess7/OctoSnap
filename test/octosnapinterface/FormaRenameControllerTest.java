/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OctoSnapInterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import junit.framework.AssertionFailedError;
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
public class FormaRenameControllerTest {
    
    public FormaRenameControllerTest() {
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
     * Test of saveChanges method, of class FormaRenameController.
     */
    @Test
    public void testSaveChanges() {
        System.out.println("saveChanges");
        ActionEvent event = null;
        FormaRenameController instance = new FormaRenameController();
    }

    /**
     * Test of dontSaveChanges method, of class FormaRenameController.
     */
    @Test(expected = NullPointerException.class)
    public void testDontSaveChanges() {
        System.out.println("dontSaveChanges");
        ActionEvent event = null;
        FormaRenameController instance = new FormaRenameController();
        instance.dontSaveChanges(event);
    }

    /**
     * Test of initialize method, of class FormaRenameController.
     */
    @Test(expected = NullPointerException.class)
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FormaRenameController instance = new FormaRenameController();
        instance.initialize(url, rb);
    }
    
}
