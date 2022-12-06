package admin;

import java.io.Serializable;

public class Image implements Serializable {
  public byte[] imageByte;
  public int size;

    public Image(byte[] imageByte, int size) {
        this.imageByte = imageByte;
        this.size = size;
    }

}
