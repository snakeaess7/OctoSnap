/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OctoSnapInterface;

import java.io.File;
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
public class AlbumFilterTest {
    
    public AlbumFilterTest() {
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
     * Test of accept method, of class AlbumFilter.
     */
    @Test
    public void testAccept() {
        System.out.println("accept");
        File dir = null;
        String name = "";
        AlbumFilter instance = new AlbumFilter();
        boolean expResult = false;
        boolean result = instance.accept(dir, name);
        assertEquals(expResult, result);
    }
    
}
