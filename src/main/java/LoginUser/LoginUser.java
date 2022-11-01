package LoginUser;

import java.io.Serializable;

public class LoginUser implements Serializable {
    public String userName;
    public String passWord;

    public LoginUser(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
