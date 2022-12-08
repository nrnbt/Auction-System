package client;

import java.io.Serializable;

import types.AuctionWithImg;

public class GetAuctionResponse implements Serializable {

    public AuctionWithImg auctionData;

    public GetAuctionResponse(AuctionWithImg auctionData) {
        this.auctionData = auctionData;
    }

    public AuctionWithImg getAuction() {
        return auctionData;
    }

    public void setAuction(AuctionWithImg auctionData) {
        this.auctionData = auctionData;
    }
}
