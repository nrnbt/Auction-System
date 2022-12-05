/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.io.Serializable;

/**
 *
 * @author nrnbt_
 */
public class GetImageResponse implements Serializable {
  public Image image;

  public GetImageResponse(Image image) {
    this.image = image;
  }

  public Image getImg() {
    return image;
  }

  public void setImg(Image image) {
    this.image = image;
  }
}
