package admin;

import java.io.Serializable;

public class GetBidsByAuctionIdRequest implements Serializable {
    public int auctionId;

    public GetBidsByAuctionIdRequest(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getUserId() {
        return auctionId;
    }

    public void setUserId(int auctionId) {
        this.auctionId = auctionId;
    }
}
