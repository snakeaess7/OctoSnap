package Manager;

import DataElement.Album;
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
        //TODO očitava sve stare serializovane Albume iz datoteka
        //i smješta ih u albums.
        
        //Treba rješiti update atributa currentAlbum.
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

}