/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import octosnapinterface.Photo;
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
public class PhotoTest {
    
    public PhotoTest() {
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
     * Test of getUrl method, of class Photo.
     */
    @Test(expected = NullPointerException.class)
    public void testGetUrl() {
        System.out.println("getUrl");
        Photo instance = null;
        String expResult = "";
        String result = instance.getUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Photo.
     */
    @Test(expected = NullPointerException.class)
    public void testGetName() {
        System.out.println("getName");
        Photo instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    
}
