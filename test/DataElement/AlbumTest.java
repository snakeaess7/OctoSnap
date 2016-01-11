/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataElement;

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
public class AlbumTest {
    
    public AlbumTest() {
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
     * Test of save method, of class Album.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Album instance = new Album("","");
        instance.save();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPhoto method, of class Album.
     */
    @Test
    public void testAddPhoto() {
        System.out.println("addPhoto");
        Photo p = null;
        Album instance = new Album("",null);
        boolean expResult = false;
        boolean result = instance.addPhoto(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of deletePhoto method, of class Album.
     */
    @Test
    public void testDeletePhoto() {
        System.out.println("deletePhoto");
        Photo p = null;
        Album instance = new Album("","");
        instance.addPhoto(null);
        boolean expResult = false;
        boolean result = instance.deletePhoto(p);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of rename method, of class Album.
     */
    @Test
    public void testRename() {
        System.out.println("rename");
        String newName = null;
        Album instance = new Album("","");
        boolean expResult = false;
        boolean result = instance.rename(newName);
        assertEquals(expResult, result);
        
    }
    
}
