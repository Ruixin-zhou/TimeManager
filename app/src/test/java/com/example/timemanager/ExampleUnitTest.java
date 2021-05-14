package com.example.timemanager;

import com.example.timemanager.ClassTable.pojo.Class_Table_Course;
import com.example.timemanager.ClassTable.view.TimeTableView;
import com.example.timemanager.ClassTable.activity.ClassMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testReflect(){
        TimeTableView timeTable = new TimeTableView(new ClassMainActivity());
        Map<String, Integer> map = new HashMap<>();
        map.put("maxSection", 100);
        map.put("radius", 20);
        map.put("numberSize", 30);
        timeTable.initParams(map);
        System.out.println(timeTable);
    }

    @Test
    public void testJson() throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();

        List<Class_Table_Course> classTableCourseList = new ArrayList<>();
        classTableCourseList.add((new Class_Table_Course("形式与政策", "肖惠朝", 1, 7, "3:5:s:XC-D")));
        classTableCourseList.add((new Class_Table_Course("计算机信息安全", "李照明", 1, 11, "3:5:d:XC207;5:3:n:XC205")));
        classTableCourseList.add((new Class_Table_Course("软件工程", "彭惠玲", 1, 12, "2:3:n:XC412;5:1:n:XC308")));
        classTableCourseList.add((new Class_Table_Course("Web前端开发", "罗豪凡", 1, 12, "1:5:n:XC412;3:1:n:XC412")));
        classTableCourseList.add((new Class_Table_Course("JavaWeb高级编程", "刁文广", 5, 16, "1:3:n:XC412;3:7:n:XC412")));
        classTableCourseList.add((new Class_Table_Course("Android应用开发", "卢向华", 5, 16, "4:3:n:XD110;5:5:n:XC310")));
        classTableCourseList.add((new Class_Table_Course("计算机视觉应用", "王国强", 1, 8, "2:5:n:XC305;4:1:n:XC409")));
        classTableCourseList.add((new Class_Table_Course("计算机新技术", "李淳凡", 6, 9, "1:1:n:XC4;1:7:n:XC4;2:1:n:XC4;3:3:n:XC4")));

        JSONArray courses = JSON.parseArray(JSON.toJSONString(classTableCourseList));

        jsonObject.put("courses", courses);
        jsonObject.put("max_section", 9);
        jsonObject.put("radius", 8);
        jsonObject.put("table_line_width", 1);
        jsonObject.put("number_size", 14);
        jsonObject.put("title_size", 18);
        jsonObject.put("course_size", 12);
        jsonObject.put("button_size", 12);
        jsonObject.put("cell_height", 75);
        jsonObject.put("title_height", 30);
        jsonObject.put("number_width", 20);

        // List<Class_Table_Course> cs = JSON.parseArray(jsonObject.getJSONArray("courses").toJSONString(), Class_Table_Course.class);
        // System.out.println(cs.toString());
    }

    @Test
    public void testWeek() throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-2-24");
        System.out.println(startDate.getTime());
    }
}