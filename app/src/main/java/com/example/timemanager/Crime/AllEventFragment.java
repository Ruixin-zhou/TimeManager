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
import com.example.timemanager.Meeting.Meeting;
import com.example.timemanager.Meeting.MeetingActivity;
import com.example.timemanager.Meeting.MeetingFragment;
import com.example.timemanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AllEventFragment extends Fragment {
    private static final int REQUEST_MEETING = 2;
    private static final int REQUEST_STUDY = 3;
    private static final int REQUEST_DAILY = 4;
    private static final String DIALOG_MEETING_TIME = "DialogMeetingTime";

    private Meeting mMeeting;

    private TextView mTodayDateText;
    private TextView mMeetingTimeText;
    private TextView mStudyText;
    private TextView mDailyText;

    private Button mClassButton;
    private Button mStudyButton;
    private Button mMeettingButton;
    private Button mDailyButton;

    private static String mWay;


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
        mMeetingTimeText = (TextView)view.findViewById(R.id.meeting_time_text) ;
        mStudyText = (TextView)view.findViewById(R.id.study_text) ;
        mDailyText = (TextView)view.findViewById(R.id.daily_text);

        Calendar calendarSaledefaultEnd = new GregorianCalendar();
        calendarSaledefaultEnd.setTime(new Date());
        calendarSaledefaultEnd.add(calendarSaledefaultEnd.DATE, 0);
        String time = new SimpleDateFormat("yyyy-MM-dd")
                .format(calendarSaledefaultEnd.getTime());
        final Calendar calendar = Calendar.getInstance();
        //calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        switch (date){
            case 2 :
                mWay = "一";
                break;
            case 3:
                mWay = "二";
                break;
            case 4 :
                mWay = "三";
                break;
            case 5:
                mWay = "四";
                break;
            case 6 :
                mWay = "五";
                break;
            case 7:
                mWay = "六";
                break;
            default:
                mWay = "日";
        }
        mTodayDateText.setText(time+" 星期"+mWay);

        mClassButton = (Button)view.findViewById(R.id.course_all_button);
        mStudyButton = (Button)view.findViewById(R.id.study_button);
        mMeettingButton = (Button)view.findViewById(R.id.meetting_time_button);
        mDailyButton = (Button)view.findViewById(R.id.daily_time_button);


        mClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClassMainActivity.class);
                startActivity(i);
            }
        });
        mStudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StudyListActivity.class);
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
    }
}
