package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourseEntryActivity extends FragmentActivity {

    private Button mBackButton;
    private Button mTimeButtom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_entry);

        mBackButton = (Button)findViewById(R.id.manager_back);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseEntryActivity.this.finish();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.entry_fragment_container);

        if (fragment == null) {
            fragment = new CourseFragment();
            fm.beginTransaction().add(R.id.entry_fragment_container,fragment).commit();
        }
    }
}
