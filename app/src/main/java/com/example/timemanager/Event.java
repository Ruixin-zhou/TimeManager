package com.example.timemanager;

import java.util.UUID;

public class Event {
    private UUID mId;
    private String mEventName;
    private String mEventTime;

    public Event() {
        mId = UUID.randomUUID();
    }

    public void setmEventName(String EventName) { mEventName = EventName;}
    public void setmEventTime(String EventTime) { mEventTime = EventTime;}

    public String getmEventName() { return mEventName; }
    public String getmEventTime() { return mEventTime; }
}

