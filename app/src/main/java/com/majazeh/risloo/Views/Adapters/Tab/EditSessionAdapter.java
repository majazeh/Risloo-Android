package com.majazeh.risloo.Views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.fragments.main.tab.EditSessionTabPaymentFragment;
import com.majazeh.risloo.Views.fragments.main.tab.EditSessionTabPlatformFragment;
import com.majazeh.risloo.Views.fragments.main.tab.EditSessionTabReferenceFragment;
import com.majazeh.risloo.Views.fragments.main.tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.fragments.main.tab.EditSessionTabTimeFragment;

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
                    Fragment fragment = new EditSessionTabTimeFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 1: {
                    Fragment fragment = new EditSessionTabSessionFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 2: {
                    Fragment fragment = new EditSessionTabPlatformFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 3: {
                    Fragment fragment = new EditSessionTabPaymentFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } default:
                    return null;
            }
        } else {
            switch (position) {
                case 0: {
                    Fragment fragment = new EditSessionTabTimeFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 1: {
                    Fragment fragment = new EditSessionTabReferenceFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 2: {
                    Fragment fragment = new EditSessionTabSessionFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 3: {
                    Fragment fragment = new EditSessionTabPlatformFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 4: {
                    Fragment fragment = new EditSessionTabPaymentFragment();
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