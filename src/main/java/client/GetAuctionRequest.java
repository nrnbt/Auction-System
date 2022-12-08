package client;

import java.io.Serializable;

public class GetAuctionRequest implements Serializable {
    public int id;

    public GetAuctionRequest(int id) {
        this.id = id;
    }

    public int getStr() {
        return id;
    }

    public void setStr(int id) {
        this.id = id;
    }
    
}
