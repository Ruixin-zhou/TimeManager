package com.example.timemanager.Meeting;

import androidx.fragment.app.Fragment;

import com.example.timemanager.Crime.SingleFragmentActivity;

public class MeetingActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MeetingFragment();
    }
}
