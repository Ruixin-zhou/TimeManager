package com.example.timemanager.Crime;

import androidx.fragment.app.Fragment;

public class DailyListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new DailyListFragment();
    }
}