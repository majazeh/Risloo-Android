package com.majazeh.risloo.views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabReference;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPayment;

import java.util.HashMap;

public class AdapterCreateSchedule extends FragmentStateAdapter {

    // Objects
    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public AdapterCreateSchedule(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new FragmentCreateScheduleTabTime();
                hashMap.put(position, fragment);
                return fragment;
            } case 1: {
                Fragment fragment = new FragmentCreateScheduleTabReference();
                hashMap.put(position, fragment);
                return fragment;
            } case 2: {
                Fragment fragment = new FragmentCreateScheduleTabSession();
                hashMap.put(position, fragment);
                return fragment;
            } case 3: {
                Fragment fragment = new FragmentCreateScheduleTabPlatform();
                hashMap.put(position, fragment);
                return fragment;
            } case 4: {
                Fragment fragment = new FragmentCreateScheduleTabPayment();
                hashMap.put(position, fragment);
                return fragment;
            } default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}