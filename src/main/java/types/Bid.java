package types;

import java.io.Serializable;

public class Bid implements Serializable {
    public int id;
    public String auctionId;
    public String userId;    
    public String userName;
    public String price;
    public String createdAt;

    public Bid(int id,
            String auctionId,
            String userId,
            String userName,
            String price,
            String createdAt) {
        this.id = id;
        this.auctionId = auctionId;
        this.userId = userId;
        this.userName = userName;
        this.price = price;
        this.createdAt = createdAt;
    }

}
