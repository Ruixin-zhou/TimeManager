package com.example.timemanager.ClassTable.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timemanager.ClassTable.adapter.LessonAdapter;
import com.example.timemanager.ClassTable.dao.CourseDao;
import com.example.timemanager.R;
import com.example.timemanager.ClassTable.pojo.Class_Table_Course;

import java.util.Iterator;
import java.util.List;

public class UpdateCourseActivity extends AppCompatActivity {

    private CourseDao courseDao = new CourseDao(this);
    private ListView mListView;
    private Class_Table_Course classTableCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);
        mListView = findViewById(R.id.lvLesson);

        classTableCourse = (Class_Table_Course) getIntent().getSerializableExtra("classTableCourse");
        showLessonInfo();
        showCourseInfo();
    }

    /**
     * 显示课程信息
     */
    private void showCourseInfo() {
        ((TextView) findViewById(R.id.tvCourseName)).setText(classTableCourse.getCourseName());
        ((EditText) findViewById(R.id.etCourseName)).setText(classTableCourse.getCourseName());
        ((EditText) findViewById(R.id.etTeacherName)).setText(classTableCourse.getTeacherName());
        ((EditText) findViewById(R.id.etStartWeek)).setText(String.valueOf(classTableCourse.getStartWeek()));
        ((EditText) findViewById(R.id.etEndWeek)).setText(String.valueOf(classTableCourse.getEndWeek()));
    }

    /**
     * 显示课次信息
     */
    private void showLessonInfo() {
        List<Class_Table_Course> courseList = classTableCourse.toDetail();
        if (courseList == null) return;
        LessonAdapter adapter = new LessonAdapter(this, courseList, new DeleteListener(courseList));
        mListView.setAdapter(adapter);
        setListViewHeight(mListView);
    }

    private class DeleteListener implements View.OnClickListener {

        private List<Class_Table_Course> courseList;
        public DeleteListener(List<Class_Table_Course> courseList){
            this.courseList = courseList;
        }

        @Override
        public void onClick(final View v) {
            new AlertDialog.Builder(UpdateCourseActivity.this)
                    .setTitle("删除课次")
                    .setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteLesson(v, courseList);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();

        }
    }

    private void deleteLesson(View v, List<Class_Table_Course> courseList){
        Class_Table_Course course = (Class_Table_Course) v.getTag();
        Iterator<Class_Table_Course> iterator = courseList.iterator();
        while (iterator.hasNext()) {
            Class_Table_Course c = iterator.next();
            if (c.getDay() == course.getDay() &&
                    c.getSection() == course.getSection() &&
                    c.getWeekType().equals(course.getWeekType()) &&
                    c.getClassroom().equals(course.getClassroom())) {
                iterator.remove();
                break;
            }
        }
        Class_Table_Course toCourse = Class_Table_Course.toCourse(courseList, UpdateCourseActivity.this.classTableCourse.getId());
        if (null != toCourse) {
            int update = courseDao.update(toCourse);
            if (update > 0) {
                String time = toCourse.getCourseTime();
                UpdateCourseActivity.this.classTableCourse.setCourseTime(time);
                Toast.makeText(UpdateCourseActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                showLessonInfo();
                return;
            }
        }
        Toast.makeText(UpdateCourseActivity.this, "固定课表无法删除！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置ListView高度
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        //总高度
        int totalHeight = 0;
        //测量并累加高度
        int count = listAdapter.getCount();
        if (count > 0) {
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() * count;
        }
        //设置高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        this.finish();
    }

    /**
     * 添加课次
     *
     * @param view
     */
    public void addLesson(View view) {
        final View inflate = getLayoutInflater().inflate(R.layout.add_lesson_item, null);
        new AlertDialog.Builder(this)
                .setTitle("添加课次")
                .setView(inflate)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //封装信息
                        Class_Table_Course course = new Class_Table_Course();
                        //星期
                        String day = ((EditText) inflate.findViewById(R.id.tvDay)).getText().toString();
                        if (!TextUtils.isEmpty(day)) {
                            course.setDay(Integer.parseInt(day));
                        } else {
                            Toast.makeText(UpdateCourseActivity.this, "星期不可为空！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //节次
                        String section = ((EditText) inflate.findViewById(R.id.tvSection)).getText().toString();
                        if (!TextUtils.isEmpty(section)) {
                            course.setSection(Integer.parseInt(section));
                        } else {
                            Toast.makeText(UpdateCourseActivity.this, "节次不可为空！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //单双周
                        int rbId = ((RadioGroup) inflate.findViewById(R.id.rgWeekType)).getCheckedRadioButtonId();
                        if (R.id.rbSingleWeek == rbId) {
                            course.setWeekType("s");
                        } else if (R.id.rbNormalWeek == rbId) {
                            course.setWeekType("n");
                        } else if (R.id.rbDoubleWeek == rbId) {
                            course.setWeekType("d");
                        } else {
                            Toast.makeText(UpdateCourseActivity.this, "周类型尚未选择！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //教室
                        String classroom = ((EditText) inflate.findViewById(R.id.tvClassroom)).getText().toString();
                        if (!TextUtils.isEmpty(classroom)) {
                            course.setClassroom(classroom);
                        } else {
                            Toast.makeText(UpdateCourseActivity.this, "教室尚未填写！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //组装上课时间
                        String courseTime = UpdateCourseActivity.this.classTableCourse.getCourseTime();
                        int id = UpdateCourseActivity.this.classTableCourse.getId();
                        if (TextUtils.isEmpty(courseTime)) {
                            course.setCourseTime(course.toTime());
                        } else {
                            course.setCourseTime(courseTime + ";" + course.toTime());
                        }
                        course.setId(id);
                        //修改
                        int update = courseDao.update(course);
                        if (update > 0) {
                            UpdateCourseActivity.this.classTableCourse.setCourseTime(course.getCourseTime());
                            Toast.makeText(UpdateCourseActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                            showLessonInfo();
                        } else {
                            Toast.makeText(UpdateCourseActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    /**
     * @param view
     */
    public void delCourse(View view) {
        new AlertDialog.Builder(this)
                .setTitle("删除课程")
                .setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int delete = courseDao.delete(classTableCourse.getId());
                        if (delete > 0) {
                            Toast.makeText(UpdateCourseActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            UpdateCourseActivity.this.finish();
                        } else {
                            Toast.makeText(UpdateCourseActivity.this, "固定课表无法删除！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    /**
     * 保存修改
     *
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void save(View view) {
        //封装信息
        Class_Table_Course course = new Class_Table_Course();
        //id
        course.setId(this.classTableCourse.getId());
        //课程名
        String courseName = ((EditText) findViewById(R.id.etCourseName)).getText().toString();
        if (!TextUtils.isEmpty(courseName)) {
            course.setCourseName(courseName);
        }
        //老师名
        String teacherName = ((EditText) findViewById(R.id.etTeacherName)).getText().toString();
        if (!TextUtils.isEmpty(teacherName)) {
            course.setTeacherName(teacherName);
        }
        course.setStartWeek(Integer.parseInt(((EditText) findViewById(R.id.etStartWeek)).getText().toString()));
        course.setEndWeek(Integer.parseInt(((EditText) findViewById(R.id.etEndWeek)).getText().toString()));
        if (this.classTableCourse.equals(course)) {
            Toast.makeText(this, "您尚未修改课程信息\n无需保存！", Toast.LENGTH_SHORT).show();
        } else {
            int update = courseDao.update(course);
            if (update > 0) {
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "修改失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
