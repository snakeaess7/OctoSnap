package DataElement;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Album implements Serializable{
    
    /**
     * Constructor
     * @param name
     * @param description
     */
    public Album(String name, String description) {
        this.date=new Date();
        
        if(name!=null)this.name=name;
        else this.name="Album "+ date.toString() ;
        
        if(description!=null)this.description=description;
        else this.description="";
        
        this.photos=new HashSet<>();
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
    private Set<Photo> photos;



    /**
     * 
     * @return 
     */
    public boolean save() {
        ObjectOutputStream out=null;
        try(){
        }catch(){
        }
    }

    /**
     * @param p
     * @return 
     */
    public boolean addPhoto(Photo p) {
        if(p!=null)return (photos.add(p));
        return false;
    }

    /**
     * @param p
     * @return 
     */
    public boolean deletePhoto(Photo p) {
        return (photos.remove(p));
    }

    /**
     * 
     * @param newName
     * @return 
     */
    public boolean rename(String newName) {
        if(newName!=null) {
            this.name=newName;
            return true;
        }
        return false;
    }
    
    private java.io.File search(){
        java.io.File dir=new java.io.File("."+java.io.File.separator+"albums");
        if(!dir.isDirectory())dir.mkdir();
        String albumName=new String(this.date.toString()+".album");
        java.io.File [] files= dir.listFiles(new AlbumFilter());
        for(java.io.File f: files){
            if(f.getName().equals(albumName)) return f;
        }
        return null;
    }

}