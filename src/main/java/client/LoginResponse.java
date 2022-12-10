package client;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public int userId;

    public LoginResponse(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
