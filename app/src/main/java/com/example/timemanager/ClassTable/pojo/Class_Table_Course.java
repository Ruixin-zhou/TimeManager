package com.example.timemanager.ClassTable.pojo;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Class_Table_Course implements Cloneable, Serializable {
    private int id;
    private String courseName;//课程名
    private String teacherName;//教师名
    //格式：星期-节次-单双周-房号
    private String courseTime;//上课时间
    private int startWeek;//开始周次
    private int endWeek;//结束周次
    private int useMinute;//需要花费的时间

    private String classroom;//教室
    private String weekType;//单双周类型
    private int day;//星期几
    private int section;//节次


    public Class_Table_Course() {
    }

    public Class_Table_Course(int id,String courseName, String teacherName, int startWeek, int endWeek, int useMinute, String courseTime) {
        this.id = id;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.courseTime = courseTime;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.useMinute = useMinute;
    }

    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getUseMinute() { return useMinute; }

    public void setUseMinute(int useMinute) {this.useMinute = useMinute;}

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }


    public Class_Table_Course clone() {
        try {
            return (Class_Table_Course) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Class_Table_Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", classroom='" + classroom + '\'' +
                ", weekType='" + weekType + '\'' +
                ", day=" + day +
                ", section=" + section +
                ", startWeek=" + startWeek +
                ", endWeek=" + endWeek +
                ", courseTime='" + courseTime + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class_Table_Course classTableCourse = (Class_Table_Course) o;
        return startWeek == classTableCourse.startWeek &&
                endWeek == classTableCourse.endWeek &&
                Objects.equals(courseName, classTableCourse.courseName) &&
                Objects.equals(teacherName, classTableCourse.teacherName);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(courseName, teacherName, startWeek, endWeek);
    }

    public List<Class_Table_Course> toDetail() {
        List<Class_Table_Course> classTableCourseList = new ArrayList<>();
        if (TextUtils.isEmpty(courseTime)) return classTableCourseList;
        String[] courseArray = courseTime.split(";");
        for (int i = 0; i < courseArray.length; i++) {
            Class_Table_Course clone = this.clone();
            String[] info = courseArray[i].split(":");

            clone.setDay(Integer.parseInt(info[0]));
            clone.setSection(Integer.parseInt(info[1]));
            clone.setWeekType(info[2]);
            clone.setClassroom(info[3]);

            classTableCourseList.add(clone);
        }
        return classTableCourseList;
    }

    public String toTime(){
        return String.format("%d:%d:%s:%s", day, section, weekType, classroom);
    }

    public static Class_Table_Course toCourse(List<Class_Table_Course> classTableCourseList, int id){
        if(null == classTableCourseList)return null;
        Class_Table_Course classTableCourse = new Class_Table_Course();
        classTableCourse.setId(id);
        StringBuffer sb = new StringBuffer();
        for(int i = 0, len = classTableCourseList.size(); i < len; i++){
            sb.append(classTableCourseList.get(i).toTime());
            if(i != len - 1)sb.append(";");
        }
        classTableCourse.setCourseTime(String.valueOf(sb));
        return classTableCourse;
    }

    public static void AddExample(int flag,List<Class_Table_Course> classTableCourseList){
        classTableCourseList.add((new Class_Table_Course(flag+1,"材料力学", "靳玉佳", 1, 12,120, "1:1:n:WH2203;5:1:n:WH2203;3:3:n:WH2203")));
        classTableCourseList.add((new Class_Table_Course(flag+2,"大学英语", "杜瑜皎", 1, 14,30, "1:5:n:WX3202;4:1:n:WX2402")));
        classTableCourseList.add((new Class_Table_Course(flag+3,"数字电子技术基础", "楚岩", 1, 16,90, "1:7:n:WM1508;3:1:n:WM1508")));
        classTableCourseList.add((new Class_Table_Course(flag+4,"概率论与数理统计", "王明辉", 1, 12,60, "2:1:n:WM3201;4:3:n:WM3201")));
        classTableCourseList.add((new Class_Table_Course(flag+5,"体育（篮球）", "马敏", 1, 18,0, "2:3:n:体育馆一楼篮球馆")));
        classTableCourseList.add((new Class_Table_Course(flag+6,"流体力学", "朱文锋", 1, 16,30, "2:5:n:WM3303")));
        classTableCourseList.add((new Class_Table_Course(flag+7,"毛泽东思想和中国特色社会主义理论体系概论", "薛睿", 1, 16,10, "2:7:n:WM1201;4:5:n:WM3201")));
        classTableCourseList.add((new Class_Table_Course(flag+8,"计算方法", "孙东霖", 1, 13,20, "3:7:n:WH1103;5:3:n:WH1103")));
    }
}
