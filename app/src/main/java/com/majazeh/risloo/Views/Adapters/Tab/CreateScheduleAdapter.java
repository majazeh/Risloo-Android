package com.majazeh.risloo.Views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.fragments.main.tab.CreateScheduleTabPlatformFragment;
import com.majazeh.risloo.Views.fragments.main.tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.Views.fragments.main.tab.CreateScheduleTabReferenceFragment;
import com.majazeh.risloo.Views.fragments.main.tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.fragments.main.tab.CreateScheduleTabPaymentFragment;

import java.util.HashMap;

public class CreateScheduleAdapter extends FragmentStateAdapter {

    // Objects
    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public CreateScheduleAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new CreateScheduleTabTimeFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 1: {
                Fragment fragment = new CreateScheduleTabReferenceFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 2: {
                Fragment fragment = new CreateScheduleTabSessionFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 3: {
                Fragment fragment = new CreateScheduleTabPlatformFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 4: {
                Fragment fragment = new CreateScheduleTabPaymentFragment();
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