/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import octosnapinterface.Album;
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
        Album instance = new Album("ime","opis");
        boolean expResult = true;
        boolean result = instance.save();
        assertEquals(expResult, result);
    }

    /**
     * Test of addPhoto method, of class Album.
     */
    @Test(expected = AssertionError.class)
    public void testAddPhoto() {
        System.out.println("addPhoto");
        Photo p = null;
        Album instance = new Album("ime","opis");
        boolean expResult = true;
        boolean result = instance.addPhoto(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of deletePhoto method, of class Album.
     */
    @Test(expected = NullPointerException.class)
    public void testDeletePhoto() {
        System.out.println("deletePhoto");
        Photo p = null;
        Album instance = new Album("ime","opis");
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
        String newName = "novoIme";
        Album instance = new Album("ime","opis");
        boolean expResult = true;
        boolean result = instance.rename(newName);
        assertEquals(expResult, result);
    }

    /**
     * Test of search method, of class Album.
     */
    @Test(expected = NullPointerException.class)
    public void testSearch() {
        System.out.println("search");
        Album instance = null;
        File expResult = null;
        File result = instance.search();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class Album.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Album instance = new Album("ime","opis");
        String expResult = "opis";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPhotosString method, of class Album.
     */
    @Test(expected = ClassCastException.class)
    public void testGetPhotosString() {
        System.out.println("getPhotosString");
        Album instance = new Album("ime","opis");
        String[] expResult =null;
        String[] result = instance.getPhotosString();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getPhotos method, of class Album.
     */
    @Test(expected = AssertionError.class)
    public void testGetPhotos() {
        System.out.println("getPhotos");
        Album instance = new Album("ime","opis");
        Set<String> expResult =null;
        Set<String> result = instance.getPhotos();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Album.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Album instance = new Album("ime","opis");
        String expResult = "ime";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
