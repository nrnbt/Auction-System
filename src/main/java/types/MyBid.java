package types;

import java.io.Serializable;

public class MyBid implements Serializable {
    public int id;
    public String bidAmount;
    public String AuctionTitle;
    public byte[] AuctionImg;

    public MyBid(int id,
            String bidAmount,
            String AuctionTitle,
            byte[] AuctionImg) {
        this.id = id;
        this.bidAmount = bidAmount;
        this.AuctionTitle = AuctionTitle;
        this.AuctionImg = AuctionImg;
    }

}
