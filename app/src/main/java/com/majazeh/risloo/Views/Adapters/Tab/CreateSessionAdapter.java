package com.majazeh.risloo.Views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabTimeFragment;

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
                Fragment fragment = new CreateSessionTabTimeFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 1: {
                Fragment fragment = new CreateSessionTabSessionFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 2: {
                Fragment fragment = new CreateSessionTabPlatformFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 3: {
                Fragment fragment = new CreateSessionTabPaymentFragment();
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