/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeview;

import javafx.scene.image.Image;

/**
 *
 * @author Octo Muerto
 */
public class Photo extends Image {
    private String url;
    
    public Photo(String url) {
        super(url);
        this.url=url;
    }
    
    public String getUrl() {
    return url;
    }
    
    
}
