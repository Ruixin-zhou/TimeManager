package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.example.timemanager.Crime.CrimeLab;
import com.example.timemanager.Crime.CrimeListActivity;

public class AllEventActivity extends AppCompatActivity {

    private Button mClassButton;
    private Button mStudyButton;
    private Button mMeettingButton;
    private Button mDailyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mStudyButton = (Button)findViewById(R.id.study_button);
        mMeettingButton = (Button)findViewById(R.id.meetting_time_button);
        mDailyButton = (Button)findViewById(R.id.daily_time_button);


        mStudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllEventActivity.this, CrimeListActivity.class);
                startActivity(i);
            }
        });

        mDailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllEventActivity.this, CrimeListActivity.class);
                startActivity(i);
            }
        });

    }


}
