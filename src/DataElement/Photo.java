package DataElement;

import javafx.scene.image.Image;

/**
 *
 * @author Octo Muerto
 */
public class Photo extends Image {

    private String url;

    public Photo(String url) {
        super(url);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
