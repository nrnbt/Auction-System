package client;

import java.io.Serializable;
import java.util.ArrayList;

import types.Bid;

public class GetBidsResponse implements Serializable {

    public ArrayList<Bid> bidsData;

    public GetBidsResponse(ArrayList<Bid>  bidsData) {
        this.bidsData = bidsData;
    }

    public ArrayList<Bid>  getAuction() {
        return bidsData;
    }

    public void setAuction(ArrayList<Bid>  bidsData) {
        this.bidsData = bidsData;
    }
}
