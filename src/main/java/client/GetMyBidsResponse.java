package client;

import java.io.Serializable;
import java.util.ArrayList;
import types.MyBid;

public class GetMyBidsResponse implements Serializable {

    public ArrayList<MyBid> myBidsList;

    public GetMyBidsResponse(ArrayList<MyBid> myBidsList) {
        this.myBidsList = myBidsList;
    }

    public ArrayList<MyBid> getMyBids() {
        return myBidsList;
    }

    public void setMyBids(ArrayList<MyBid> myBidsList) {
        this.myBidsList = myBidsList;
    }
}
