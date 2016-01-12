/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import DataElement.Album;
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
public class AlbumManagerTest {
    
    public AlbumManagerTest() {
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
     * Test of newAlbum method, of class AlbumManager.
     */
    @Test
    public void testNewAlbum() {
        System.out.println("newAlbum");
        String name = "";
        String description = "";
        AlbumManager instance = new AlbumManager();
        boolean expResult = true;
        boolean result = instance.newAlbum(name, description);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of delete method, of class AlbumManager.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Album a = new Album("","");
        AlbumManager instance = new AlbumManager();
        boolean expResult = false;
        boolean result = instance.delete(a);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setCurrentAlbum method, of class AlbumManager.
     */
    @Test
    public void testSetCurrentAlbum() {
        System.out.println("setCurrentAlbum");
        Album currentAlbum = null;
        AlbumManager instance = new AlbumManager();
        instance.setCurrentAlbum(currentAlbum);
    }

    /**
     * Test of readAlbums method, of class AlbumManager.
     */
    @Test
    public void testReadAlbums() {
        System.out.println("readAlbums");
        AlbumManager instance = new AlbumManager();
        instance.readAlbums();
    }
    
}
