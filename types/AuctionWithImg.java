package types;

import java.io.Serializable;

public class AuctionWithImg implements Serializable {
    public int id;
    public String title;
    public String user;
    public String userId;
    public String startPrice;
    public String endPrice;
    public String startTime;
    public String endTime;
    public String status;
    public byte[] img;
    public String winner;
    public String description;

    public AuctionWithImg(int id,
            String title,
            String user,
            String userId,
            String startPrice,
            String endPrice,
            String startTime,
            String endTime,
            String status,
            byte[] img,
            String winner,
            String description) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.userId = userId;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.img = img;
        this.winner = winner;
        this.description = description;
    }

}
