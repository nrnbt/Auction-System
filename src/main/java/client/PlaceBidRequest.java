package client;

import java.io.Serializable;

public class PlaceBidRequest implements Serializable {
    public int auctionId;    
    public int bidAmount;
    public int userId;

    public PlaceBidRequest(int auctionId, int bidAmount, int userId) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
        this.userId = userId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
    
    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }
    
    public int getUserId(){
        return userId;
    }
    
    public void setUserId(int userId){
        this.userId = userId;
    }
    
}
