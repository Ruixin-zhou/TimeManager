package com.example.timemanager.Crime;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DailyLab {
    private static DailyLab sCrimeLab;

    private ArrayList<Daily> mCrimes;

    public static DailyLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new DailyLab(context);
        }
        return sCrimeLab;
    }

    private DailyLab(Context context) {
        mCrimes = new ArrayList<>();
    }

    public void addCrime(Daily c) {
        mCrimes.add(c);
    }

    public void deleteCrime(Daily c){
        mCrimes.remove(c);
    }

    public List<Daily> getDailys() {
        return mCrimes;
    }

    public Daily getCrime(UUID id) {
        for (Daily crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
