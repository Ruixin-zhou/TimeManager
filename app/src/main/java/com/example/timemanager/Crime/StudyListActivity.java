package com.example.timemanager.Crime;

import androidx.fragment.app.Fragment;

public class StudyListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new StudyListFragment();
    }
}
