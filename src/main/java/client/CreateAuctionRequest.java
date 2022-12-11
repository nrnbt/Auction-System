package client;

import java.io.Serializable;

public class CreateAuctionRequest implements Serializable {
    public int userId;
    public String title;
    public String startPrice;
    public String description;
    public byte[] img;

    public CreateAuctionRequest(int userId,String title, String startPrice, String description, byte[] img) {
        this.userId = userId;
        this.title = title;
        this.startPrice = startPrice;
        this.description = description;
        this.img = img;
    }
    
    public int getUserId(){
        return userId;
    }
    
    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public byte[] getImg() {
        return img;
    }

    public void getImg(byte[] getImg) {
        this.img = img;
    }
}
