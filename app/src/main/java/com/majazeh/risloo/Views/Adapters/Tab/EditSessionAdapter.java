package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabTimeFragment;

import java.util.HashMap;

public class EditSessionAdapter extends FragmentStateAdapter {

    // Vars
    private boolean hasCase;

    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

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
                }
                case 1: {
                    Fragment fragment = new EditSessionTabSessionFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 2: {
                    Fragment fragment = new EditSessionTabPlatformFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 3: {
                    Fragment fragment = new EditSessionTabPaymentFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                default:
                    return null;
            }
        } else {
            switch (position) {
                case 0: {
                    Fragment fragment = new EditSessionTabTimeFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 1: {
                    Fragment fragment = new EditSessionTabReferenceFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 2: {
                    Fragment fragment = new EditSessionTabSessionFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 3: {
                    Fragment fragment = new EditSessionTabPlatformFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 4: {
                    Fragment fragment = new EditSessionTabPaymentFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                default:
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