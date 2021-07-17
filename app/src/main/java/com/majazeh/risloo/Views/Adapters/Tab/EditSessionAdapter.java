package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;

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
                    Fragment fragment = new EditSessionTimeFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 1: {
                    Fragment fragment = new EditSessionSessionFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 2: {
                    Fragment fragment = new EditSessionPlatformFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 3: {
                    Fragment fragment = new EditSessionPaymentFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                default:
                    return null;
            }
        } else {
            switch (position) {
                case 0: {
                    Fragment fragment = new EditSessionTimeFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 1: {
                    Fragment fragment = new EditSessionReferenceFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 2: {
                    Fragment fragment = new EditSessionSessionFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 3: {
                    Fragment fragment = new EditSessionPlatformFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                }
                case 4: {
                    Fragment fragment = new EditSessionPaymentFragment();
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