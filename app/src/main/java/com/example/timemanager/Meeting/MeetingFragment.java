package com.example.timemanager.Meeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.timemanager.Crime.AllEventFragment;
import com.example.timemanager.Crime.CrimeFragment;
import com.example.timemanager.Crime.CrimeLab;
import com.example.timemanager.Crime.TimePickerFragment;
import com.example.timemanager.R;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class MeetingFragment extends DialogFragment {
    private static final String ARG_MEETING_ID = "meeting_id";
    private static final String DIALOG_TIME = "DialogTime";

    private static final String ARG_MEETINGTIME = "time";
    public static final String EXTRA_MEETING =
            "com.example.timemanager.Meeting.message";

    private static final int REQUEST_MEETING = 2;

    private Meeting mMeeting;

    private EditText mMeetingTitle;
    private EditText mMeetingRoom;
    private TextView mMeetingTime;
    private Button mMeetingTimeButton;
    private Button mSave;
    private Button mCancel;

    public static MeetingFragment newInstance(UUID MeetingId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEETING_ID, MeetingId);

        MeetingFragment fragment = new MeetingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeeting = new Meeting();
        //UUID meetingId = (UUID) getArguments().getSerializable(ARG_MEETING_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);

        mMeetingTitle = (EditText)view.findViewById(R.id.meeting_title);
        mMeetingRoom = (EditText)view.findViewById(R.id.meeting_room);
        mMeetingTime = (TextView)view.findViewById(R.id.meeting_time_text);
        mMeetingTimeButton = (Button)view.findViewById(R.id.meeting_select_time_button);
        mSave = (Button)view.findViewById(R.id.meeting_save);
        mCancel = (Button)view.findViewById(R.id.meeting_cancel);

        mMeetingTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMeeting.setmMeetingTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mMeetingRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMeeting.setmMeetingRoom(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMeetingMessage(mMeeting);
                getActivity().finish();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mMeetingTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mMeeting.getmMeetingDate());
                dialog.setTargetFragment(MeetingFragment.this,REQUEST_MEETING);
                dialog.show(manager,DIALOG_TIME);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_MEETING) {
            int hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR,0);
            int minute = data.getIntExtra(TimePickerFragment.EXTRA_MINUTE,0);
            mMeeting.setmMeetingHour(hour);
            mMeeting.setmMeetingminute(minute);
            mMeetingTime.setText(mMeeting.getmMeetingHour()+":"+mMeeting.getmMeetingminute());
        }
    }

    public static MeetingFragment newInstance(Meeting meeting) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEETINGTIME, meeting);

        MeetingFragment fragment = new MeetingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void sendMeetingMessage(Meeting meeting){
        Intent data = new Intent();
        data.putExtra(EXTRA_MEETING,meeting);
        getActivity().setResult(Activity.RESULT_OK,data);
    }
}
