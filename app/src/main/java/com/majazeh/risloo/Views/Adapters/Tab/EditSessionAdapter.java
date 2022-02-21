package com.majazeh.risloo.views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabReference;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabTime;

import java.util.HashMap;

public class EditSessionAdapter extends FragmentStateAdapter {

    // Objects
    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    // Vars
    private boolean hasCase;

    public EditSessionAdapter(@NonNull FragmentActivity fragment, boolean hasCase) {
        super(fragment);
        this.hasCase = hasCase;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (hasCase) {
            switch (position) {
                case 0: {
                    Fragment fragment = new FragmentEditSessionTabTime();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 1: {
                    Fragment fragment = new FragmentEditSessionTabSession();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 2: {
                    Fragment fragment = new FragmentEditSessionTabPlatform();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 3: {
                    Fragment fragment = new FragmentEditSessionTabPayment();
                    hashMap.put(position, fragment);
                    return fragment;
                } default:
                    return null;
            }
        } else {
            switch (position) {
                case 0: {
                    Fragment fragment = new FragmentEditSessionTabTime();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 1: {
                    Fragment fragment = new FragmentEditSessionTabReference();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 2: {
                    Fragment fragment = new FragmentEditSessionTabSession();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 3: {
                    Fragment fragment = new FragmentEditSessionTabPlatform();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 4: {
                    Fragment fragment = new FragmentEditSessionTabPayment();
                    hashMap.put(position, fragment);
                    return fragment;
                } default:
                    return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (hasCase)
            return 4;
        else
            return 5;
    }

}