package com.example.timemanager.Crime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.timemanager.AllEventActivity;
import com.example.timemanager.ClassTable.activity.ClassMainActivity;
import com.example.timemanager.ClassTable.pojo.Class_Table_Course;
import com.example.timemanager.Meeting.Meeting;
import com.example.timemanager.Meeting.MeetingActivity;
import com.example.timemanager.Meeting.MeetingFragment;
import com.example.timemanager.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.CRC32;

import static com.example.timemanager.ClassTable.pojo.Class_Table_Course.AddExample;

public class AllEventFragment extends Fragment {
    private static final int REQUEST_MEETING = 2;
    private static final int REQUEST_STUDY = 3;
    private static final int REQUEST_DAILY = 4;
    private static final int REQUEST_ALLCLASS = 5;


    public static final String EXTRA_ALLEVENT_ALLCLASS =
            "com.example.timemanager.Crime.allclass";

    int p = 1;

    private Meeting mMeeting;

    private TextView mTodayDateText;
    private TextView mAllClassText;
    private TextView mMeetingTimeText;
    private TextView mStudyText;
    private TextView mDailyText;

    private Button mClassButton;
    private Button mStudyButton;
    private Button mMeettingButton;
    private Button mDailyButton;

    private static String mWay;

    private static List<Class_Table_Course> allClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mMeeting = new Meeting();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event, container, false);


        mTodayDateText = (TextView)view.findViewById(R.id.today_date_text);
        mAllClassText = (TextView)view.findViewById(R.id.all_course);
        mMeetingTimeText = (TextView)view.findViewById(R.id.meeting_time_text) ;
        mStudyText = (TextView)view.findViewById(R.id.study_text) ;
        mDailyText = (TextView)view.findViewById(R.id.daily_text);

        final List<Class_Table_Course> course = new ArrayList<>();

        Calendar calendarSaledefaultEnd = new GregorianCalendar();
        calendarSaledefaultEnd.setTime(new Date());
        calendarSaledefaultEnd.add(calendarSaledefaultEnd.DATE, 0);
        String time = new SimpleDateFormat("yyyy-MM-dd")
                .format(calendarSaledefaultEnd.getTime());
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        mWay = intToString(date-1);
        mTodayDateText.setText(time+" 星期"+mWay);

        mClassButton = (Button)view.findViewById(R.id.course_all_button);
        mStudyButton = (Button)view.findViewById(R.id.study_button);
        mMeettingButton = (Button)view.findViewById(R.id.meetting_time_button);
        mDailyButton = (Button)view.findViewById(R.id.daily_time_button);

        if(p == 1 ){
            AddExample(0,course);
            allClass = course;
            int m = updateClass(course);
            mStudyText.setText("共有"+m+"节自习");
            p=0;
        }

        mClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClassMainActivity.class);
                startActivityForResult(i,REQUEST_ALLCLASS);
            }
        });
        mStudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = newIntent(getActivity(),allClass);
                startActivityForResult(i,REQUEST_STUDY);
            }
        });
        mMeettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MeetingActivity.class);
                startActivityForResult(i,REQUEST_MEETING);
            }
        });
        mDailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DailyListActivity.class);
                startActivityForResult(i,REQUEST_DAILY);
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_MEETING){
            String str = mMeetingTimeText.getText().toString();
            mMeeting = (Meeting) data
                    .getSerializableExtra(MeetingFragment.EXTRA_MEETING);
            str = str + "\r\n" +mMeeting.getmMeetingHour()+":" +mMeeting.getmMeetingminute()+" "+mMeeting.getmMeetingRoom()+" "+mMeeting.getmMeetingTitle();
            mMeetingTimeText.setText(str);
        }

        if(requestCode == REQUEST_DAILY){
            int dailySum =(int)data
                    .getIntExtra(DailyListFragment.EXTRA_DAILY_SUM,0);
            String str = "共有"+dailySum+"项待办事项";
            mDailyText.setText(str);
        }

        if(requestCode == REQUEST_STUDY){
            int StugySum =(int)data
                    .getIntExtra(StudyListFragment.EXTRA_STUDY_SUM,0);
            String str = "共有"+StugySum+"节自习";
            mStudyText.setText(str);
        }

        if(requestCode == REQUEST_ALLCLASS){
            allClass = (List<Class_Table_Course>)data.getSerializableExtra(ClassMainActivity.EXTRA_CLASSTABLE_ALLCLASS);
            updateClass(allClass);
        }
    }

    public static Intent newIntent(Context packageContext, List<Class_Table_Course> useClass){
        Intent i =new Intent(packageContext,StudyListActivity.class);
        i.putExtra(EXTRA_ALLEVENT_ALLCLASS, (Serializable) useClass);
        return i;
    }

    private int updateClass(List<Class_Table_Course> allClass){
        int count = allClass.size();
        int sum = 0;
        Class_Table_Course course;
        String FinalCourseName = "";
        String FinalCourseTime = "";
        String FinalCourseRoom = "";
        String str = "";
        for(int i = 0;i<count;i++){
            course = allClass.get(i);
            String[] courseArray = course.getCourseTime().split(";");
            for (int j = 0; j < courseArray.length; j++){
                String[] info = courseArray[j].split(":");
                int flag = Integer.parseInt(info[0]);
                String day ;
                day = intToString(flag);
                if((mWay.equals("五") ||mWay.equals("六") || mWay.equals("日"))&& (flag == 1 || flag == 2) && !course.getCourseName().equals("体育（篮球）")){     //今天是星期五并且在周一与周二有课
                    sum++;
                }
                if(day.equals(mWay)){
                    FinalCourseName = course.getCourseName();
                    FinalCourseTime = info[1];
                    switch (FinalCourseTime){
                        case "1":
                            FinalCourseTime = "08:30";
                            break;
                        case "2":
                            FinalCourseTime = "09:10";
                            break;
                        case "3":
                            FinalCourseTime = "10:05";
                            break;
                        case "4":
                            FinalCourseTime = "11:15";
                            break;
                        case "5":
                            FinalCourseTime = "14:00";
                            break;
                        case "6":
                            FinalCourseTime = "14:40";
                            break;
                        case "7":
                            FinalCourseTime = "15:35";
                            break;
                            default:
                                FinalCourseTime = "19:00";

                    }
                    FinalCourseRoom = info[3];
                    str = str +FinalCourseName+" "+FinalCourseTime+" "+FinalCourseRoom+ "\n" ;
                }
            }
        }
        mAllClassText.setText(str);

        return sum;
    }

    public String intToString(int i){
        String str;
        switch (i){
            case 1 :
                str = "一";
                break;
            case 2:
                str = "二";
                break;
            case 3 :
                str = "三";
                break;
            case 4:
                str = "四";
                break;
            case 5 :
                str = "五";
                break;
            case 6:
                str = "六";
                break;
            default:
                str = "日";
        }
        return str;
    }
}
