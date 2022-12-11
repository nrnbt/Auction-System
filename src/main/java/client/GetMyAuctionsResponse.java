package client;

import java.io.Serializable;
import java.util.ArrayList;
import types.AuctionWithImg;

public class GetMyAuctionsResponse implements Serializable {

    public ArrayList<AuctionWithImg> auctionList;

    public GetMyAuctionsResponse(ArrayList<AuctionWithImg> auctionList) {
        this.auctionList = auctionList;
    }

    public ArrayList<AuctionWithImg> getAuction() {
        return auctionList;
    }

    public void setAuction(ArrayList<AuctionWithImg> auctionList) {
        this.auctionList = auctionList;
    }
}
