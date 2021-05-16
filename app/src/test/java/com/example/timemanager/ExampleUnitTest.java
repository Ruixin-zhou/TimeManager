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
        classTableCourseList.add((new Class_Table_Course("材料力学", "靳玉佳", 1, 12, "1:1:n:WH2203;5:1:n:WH2203;3:3:n:WH2203")));
        classTableCourseList.add((new Class_Table_Course("大学英语", "杜瑜皎", 1, 14, "1:5:n:WX3202;4:1:n:WX2402")));
        classTableCourseList.add((new Class_Table_Course("数字电子技术基础", "楚岩", 1, 16, "1:7:n:WM1508;3:1:n:WM1508")));
        classTableCourseList.add((new Class_Table_Course("概率论与数理统计", "王明辉", 1, 12, "2:1:n:WM3201;4:3:n:WM3201")));
        classTableCourseList.add((new Class_Table_Course("体育（篮球）", "马敏", 1, 18, "2:3:n:体育馆一楼篮球馆")));
        classTableCourseList.add((new Class_Table_Course("流体力学", "朱文锋", 1, 16, "2:5:n:WM3303")));
        classTableCourseList.add((new Class_Table_Course("毛泽东思想和中国特色社会主义理论体系概论", "薛睿", 1, 16, "2:7:n:WM1201;4:5:n:WM3201")));
        classTableCourseList.add((new Class_Table_Course("计算方法", "孙东霖", 4, 13, "3:7:n:WH1103;5:3:n:WH1103")));

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