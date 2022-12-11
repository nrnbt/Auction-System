package client;

import java.io.Serializable;

public class GetMyAuctionsRequest implements Serializable {
    public int userId;

    public GetMyAuctionsRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
