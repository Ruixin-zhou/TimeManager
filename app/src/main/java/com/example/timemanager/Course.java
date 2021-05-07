package com.example.timemanager;

import java.sql.Time;
import java.util.UUID;

public class Course {

    private UUID mId;
    private String mCourseName;
    private String mCourseTime;
    private String mCourseClassRoom;

    public Course() {
        mId = UUID.randomUUID();
    }

    public void setmCourseName(String CourseName) {
        mCourseName = CourseName;
    }
    public void setmCourseTime(String CourseTime) {
        mCourseTime = CourseTime;
    }
    public void setmCourseClassRoom(String CourseClassRoom) {
        mCourseClassRoom = CourseClassRoom;
    }

    public String getmCourseName() { return mCourseName; }
    public String getmCourseTime() { return mCourseTime; }
    public String getmCourseClassRoom() { return mCourseClassRoom; }
}
