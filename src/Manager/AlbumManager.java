package Manager;

import DataElement.Album;
import DataElement.AlbumFilter;
import java.util.*;

/**
 * 
 */
public class AlbumManager {

    /**
     * Default constructor
     */
    public AlbumManager() {
        this.albums=new HashSet<>();
        java.io.File dir=new java.io.File("."+java.io.File.separator+"albums");
        if(!dir.isDirectory())dir.mkdir();
        java.io.File [] files= dir.listFiles(new AlbumFilter());
        for(java.io.File f:files){
            
        }
        
    }

    /**
     * 
     */
    private Album currentAlbum;

    /**
     * 
     */
    private Set<Album> albums;



    /**
     * 
     * @param name
     * @param description
     * @return 
     */
    public boolean newAlbum(String name, String description) {
        Album newAlbum=null;
        if((newAlbum=new Album(name,description))!=null){
            return this.albums.add(newAlbum);
        }
        return false;
    }

    /**
     * @param a
     * @return 
     */
    public boolean delete(Album a) {
        return (albums.remove(a));
    }

    /**
     *
     * @param currentAlbum
     */
    public void setCurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }
    
    

}