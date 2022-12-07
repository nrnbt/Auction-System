/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.io.Serializable;
/**
 *
 * @author nrnbt
 */
public class UpdateAuctionWinnerRequest implements Serializable {
    public int id;
    public String winner;

    public UpdateAuctionWinnerRequest(int id, String winner){
        this.id = id;
        this.winner = winner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
