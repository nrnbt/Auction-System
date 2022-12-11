package admin;

import java.io.Serializable;
import java.util.ArrayList;
import types.Bid;

public class GetBidsByAuctionIdResponse implements Serializable {

    public ArrayList<Bid> bidsList;

    public GetBidsByAuctionIdResponse(ArrayList<Bid> bidsList) {
        this.bidsList = bidsList;
    }

    public ArrayList<Bid> getMyBids() {
        return bidsList;
    }

    public void setMyBids(ArrayList<Bid> bidsList) {
        this.bidsList = bidsList;
    }
}
