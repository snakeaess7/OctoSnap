package Manager;

import DataElement.Album;
import DataElement.AlbumFilter;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

/**
 *
 * @author Sanja
 */
public class AlbumManager {

    /**
     * Default constructor
     */
    public AlbumManager() {
        this.albums = new HashSet<>();
        readAlbums();
        setCurrentAlbum(null);
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
        Album newAlbum = null;
        if ((newAlbum = new Album(name, description)) != null) {
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

    private void readAlbums() {
        java.io.File dir = new java.io.File("." + java.io.File.separator + "albums");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        java.io.File[] files = dir.listFiles(new AlbumFilter());
        ObjectInputStream in = null;
        for (java.io.File f : files) {
            try {
                in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
                Album a = null;
                a = (Album) in.readObject();
                if (a != null) {
                    albums.add(a);
                }
                in.close();
            } catch (Exception e) {
            }
        }
    }

}
