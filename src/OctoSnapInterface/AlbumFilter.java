/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octosnapinterface;

import java.io.FilenameFilter;

/**
 *
 * @author Sanja
 */
public class AlbumFilter implements FilenameFilter {

    @Override
    public boolean accept(java.io.File dir, String name) {
        return name.endsWith(".album");
    }

}
