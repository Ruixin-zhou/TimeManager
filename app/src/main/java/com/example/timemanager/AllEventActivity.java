package com.example.timemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timemanager.Crime.AllEventFragment;
import com.example.timemanager.Crime.DailyListActivity;
import com.example.timemanager.Crime.SingleFragmentActivity;
import com.example.timemanager.Crime.StudyListActivity;
import com.example.timemanager.Meeting.Meeting;
import com.example.timemanager.Meeting.MeetingActivity;
import com.example.timemanager.Meeting.MeetingFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AllEventActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new AllEventFragment(); }
}
