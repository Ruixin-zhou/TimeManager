package com.example.timemanager.Crime;

import java.util.Date;
import java.util.UUID;

public class Daily {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private int mHour;
    private int mMinute;
    private boolean mSolved;

    public Daily() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() { return mDate; }

    public int getMonth() { return mDate.getMonth();}
    public String getday()
    { switch (mDate.getDay()){
        case 1 :
            return "一";
        case 2:
            return "二";
        case 3 :
            return "三";
        case 4:
            return "四";
        case 5 :
            return "五";
        case 6:
            return "六";
        default:
            return "日";
    }
    }
    public int getdate() { return mDate.getDate();}
    public int getnowhour() {return  mDate.getHours();}
    public int getnowminute() {return mDate.getMinutes();}
    public int gethour() {return  mHour;}
    public int getminute() {return mMinute;}

    public void setDate(Date date) { mDate = date;}
    public void setHour(int hour){mHour = hour;}
    public void setMinute(int minute){mMinute = minute;}

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
