package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;

import java.util.HashMap;

public class CreateScheduleAdapter extends FragmentStateAdapter {

    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public CreateScheduleAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new CreateScheduleTimeFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 1: {
                Fragment fragment = new CreateScheduleReferenceFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 2: {
                Fragment fragment = new CreateScheduleSessionFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 3: {
                Fragment fragment = new CreateSchedulePaymentFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}