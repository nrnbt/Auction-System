package client;


import java.io.Serializable;

public class GetAllAuctionRequest implements Serializable {
    public String str;
    public String filterStatus;

    public GetAllAuctionRequest(String str, String filterStatus) {
        this.str = str;
        this.filterStatus = filterStatus;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    
     public String getStatusFilter() {
        return filterStatus;
    }

    public void setStatusFilter(String filterStatus) {
        this.filterStatus = filterStatus;
    }
}
