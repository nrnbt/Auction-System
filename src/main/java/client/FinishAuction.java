/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.Serializable;

/**
 *
 * @author nrnbt
 */
public class FinishAuction implements Serializable {
     public int id;

    public FinishAuction(int id) {
        this.id = id;
    }

    public int getStr() {
        return id;
    }

    public void setStr(int id) {
        this.id = id;
    }
     
}

