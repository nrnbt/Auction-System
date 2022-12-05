package admin;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Image implements Serializable {
  public BufferedImage image;
  public byte[] size;

    public Image( BufferedImage image, byte[] size) {
        this.image = image;
        this.size = size;
    }

}
