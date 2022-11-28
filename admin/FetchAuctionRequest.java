package admin;

import java.io.Serializable;

public class FetchAuctionRequest implements Serializable {
    public String str;

    public FetchAuctionRequest(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
