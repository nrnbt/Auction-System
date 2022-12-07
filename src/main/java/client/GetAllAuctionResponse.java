package client;

import java.io.Serializable;
import java.util.ArrayList;

import types.Auction;
import types.Auction;

public class GetAllAuctionResponse implements Serializable {

    public ArrayList<Auction> auctionList;

    public GetAllAuctionResponse(ArrayList<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    public ArrayList<Auction> getAuction() {
        return auctionList;
    }

    public void setAuction(ArrayList<Auction> auctionList) {
        this.auctionList = auctionList;
    }
}
