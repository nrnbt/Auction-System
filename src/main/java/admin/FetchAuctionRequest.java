package admin;

import java.io.Serializable;

public class FetchAuction implements Serializable {
    public String str;

    public FetchAuction(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
