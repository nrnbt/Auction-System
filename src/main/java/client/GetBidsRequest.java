package client;

import java.io.Serializable;

public class GetBidsRequest implements Serializable {
    public int auctionId;

    public GetBidsRequest(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
    
}
