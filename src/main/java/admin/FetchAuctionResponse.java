package admin;

import java.io.Serializable;
import java.util.ArrayList;

public class FetchAuctionResponse implements Serializable {
    public ArrayList<Auction> auctionList;

    public FetchAuctionResponse(ArrayList<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    public ArrayList<Auction> getAuction() {
        return auctionList;
    }

    public void setAuction(ArrayList<Auction> auctionList) {
        this.auctionList = auctionList;
    }
}
