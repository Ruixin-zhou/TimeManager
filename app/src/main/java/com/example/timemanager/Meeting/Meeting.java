package com.example.timemanager.Meeting;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Meeting implements Serializable {
    private UUID mId;

    private String mMeetingTitle;
    private String mMeetingRoom;
    private Date mMeetingDate;
    private int mMeetingHour;
    private int mMeetingminute;

    public Meeting() {
        mId = UUID.randomUUID();
        mMeetingDate = new Date();
    }

    public String getmMeetingTitle() { return mMeetingTitle;}
    public String getmMeetingRoom() { return mMeetingRoom; }
    public Date getmMeetingDate() { return mMeetingDate; }
    public int getmMeetingHour() {return mMeetingHour; }
    public int getmMeetingminute() { return mMeetingminute; }

    public void setmMeetingTitle(String mMeetingTitle) { this.mMeetingTitle = mMeetingTitle; }
    public void setmMeetingRoom(String mMeetingRoom) { this.mMeetingRoom = mMeetingRoom; }
    public void setmMeetingHour(int mMeetingHour) { this.mMeetingHour = mMeetingHour; }
    public void setmMeetingminute(int mMeetingminute) { this.mMeetingminute = mMeetingminute; }

}

