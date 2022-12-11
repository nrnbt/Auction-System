package client;

import java.io.Serializable;

public class GetMyBidsRequest implements Serializable {
    public int userId;

    public GetMyBidsRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
