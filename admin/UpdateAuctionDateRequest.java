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
public class UpdateAuctionDateRequest implements Serializable {
    public int auctionId;
    public String startTime;
    public String endTime;
    public String startDay;
    public String endDay;

    public UpdateAuctionDateRequest(int auctionId, String startTime, String endTime, String startDay, String endDay) {
        this.auctionId = auctionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDay = startDay;
        this.endDay = endDay;
    }
    
    public int getId() {
        return auctionId;
    }

    public void setId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
    
     public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}
