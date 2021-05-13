package com.example.timemanager.Crime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.timemanager.R;

import java.util.Date;
import java.util.UUID;

public class DailyFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Daily mCrime;
    private EditText mTitleField;
    private TextView mDateText;
    private TextView mTimeText;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mSaveButton;
    private Button mDeleteButton;
    private CheckBox mSolvedCheckBox;

    public static DailyFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = DailyLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily, container, false);

        mDateText = (TextView) v.findViewById(R.id.crime_date_text);
        mTimeText = (TextView) v.findViewById(R.id.crime_time_text);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mTimeButton = (Button) v.findViewById(R.id.crime_time);
        mSaveButton = (Button) v.findViewById(R.id.crime_save);
        mDeleteButton = (Button) v.findViewById(R.id.crime_cancel);

        updateDate();
        //mDateText.setText(mCrime.getMonth()+1+"月"+mCrime.getdate()+"日 星期"+mCrime.getday());
        //mTimeText.setText(mCrime.getnowhour()+":"+mCrime.getnowminute());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(DailyFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(DailyFragment.this,REQUEST_TIME);
                dialog.show(manager,DIALOG_TIME);
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
                getActivity().finish();
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyLab.get(getActivity()).deleteCrime(mCrime);
                getActivity().finish();
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateText.setText(mCrime.getMonth()+1+"月"+mCrime.getdate()+"日 星期"+mCrime.getday());
            //updateDate();
        }
        if(requestCode == REQUEST_TIME) {
            int hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR,0);
            int minute = data.getIntExtra(TimePickerFragment.EXTRA_MINUTE,0);
            mCrime.setHour(hour);
            mCrime.setMinute(minute);
            updateDate();
        }
    }

    private void updateDate() {
        //mDateButton.setText(mCrime.getDate().toString());
        //mTimeButton.setText(mCrime.getDate().toString());
        mDateText.setText(mCrime.getMonth()+1+"月"+mCrime.getdate()+"日 星期"+mCrime.getday());
        mTimeText.setText(mCrime.gethour()+":"+mCrime.getminute());
    }
}
