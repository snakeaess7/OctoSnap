package DataElement;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Sanja
 */
public class Album implements Serializable {

    /**
     * Constructor
     *
     * @param name
     * @param description
     */
    public Album(String name, String description) {
        this.date = new Date();

        if (name != null) {
            this.name = name;
        } else {
            this.name = "Album " + date.toString();
        }

        if (description != null) {
            this.description = description;
        } else {
            this.description = "";
        }

        this.photos = new HashSet<>();
    }

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Date date;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private Set<String> photos;

    /**
     *
     * @return
     */
    public boolean save() {
        ObjectOutputStream out = null;
        java.io.File f = search();
        if (f == null) {
            f = new java.io.File("." + java.io.File.separator + "albums"
                    + java.io.File.separator + this.date.toString() + ".album");
        }
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f, false)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (Exception e) {
        }
        return false;

    }

    /**
     * @param p
     * @return
     */
    public boolean addPhoto(Photo p) {
        if (p != null) {
            return (photos.add(p.getUrl()));
        }
        return false;
    }

    /**
     * @param p
     * @return
     */
    public boolean deletePhoto(Photo p) {
        return (photos.remove(p.getUrl()));
    }

    /**
     *
     * @param newName
     * @return
     */
    public boolean rename(String newName) {
        if (newName != null) {
            this.name = newName;
            return true;
        }
        return false;
    }

    public java.io.File search() {
        java.io.File dir = new java.io.File("." + java.io.File.separator + "albums");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        String albumName = new String(this.date.toString() + ".album");
        java.io.File[] files = dir.listFiles(new AlbumFilter());
        for (java.io.File f : files) {
            if (f.getName().equals(albumName)) {
                return f;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String[] getPhotos() {
        return (String[]) photos.toArray();
    }

    @Override
    public String toString() {
        return "" + name + " (" + date + ")";
    }

}
