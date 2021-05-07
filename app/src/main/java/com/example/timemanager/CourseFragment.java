package com.example.timemanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CourseFragment extends Fragment {
    private Course mCourse;
    private EditText mCourseName;
    private EditText mCourseStart;
    private EditText mCourseClassroom;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourse = new Course();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_entry,container,false);

        mCourseName = (EditText)v.findViewById(R.id.course_name);
        mCourseStart = (EditText)v.findViewById(R.id.course_start);
        mCourseClassroom = (EditText)v.findViewById(R.id.course_classroom);

        mCourseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCourse.setmCourseName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mCourseStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCourse.setmCourseName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mCourseClassroom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCourse.setmCourseName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;
    }
}
