package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;

public class EventActivity extends AppCompatActivity {

    private Button mContinue;
    private Button mFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mContinue = (Button)findViewById(R.id.event_continue_input);
        mFinish = (Button)findViewById(R.id.event_finish);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.Event_entry_fragment_container);

        if (fragment == null) {
            fragment = new EventFragment();
            fm.beginTransaction().add(R.id.Event_entry_fragment_container,fragment).commit();
        }
    }
}
