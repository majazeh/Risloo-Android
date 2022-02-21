package com.majazeh.risloo.views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabTime;

import java.util.HashMap;

public class CreateSessionAdapter extends FragmentStateAdapter {

    // Objects
    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public CreateSessionAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new FragmentCreateSessionTabTime();
                hashMap.put(position, fragment);
                return fragment;
            } case 1: {
                Fragment fragment = new FragmentCreateSessionTabSession();
                hashMap.put(position, fragment);
                return fragment;
            } case 2: {
                Fragment fragment = new FragmentCreateSessionTabPlatform();
                hashMap.put(position, fragment);
                return fragment;
            } case 3: {
                Fragment fragment = new FragmentCreateSessionTabPayment();
                hashMap.put(position, fragment);
                return fragment;
            } default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}