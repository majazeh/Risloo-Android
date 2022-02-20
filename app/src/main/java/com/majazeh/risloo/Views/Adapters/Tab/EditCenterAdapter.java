package com.majazeh.risloo.views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.views.fragments.main.tab.EditCenterTabAvatarFragment;
import com.majazeh.risloo.views.fragments.main.tab.EditCenterTabDetailFragment;

import java.util.HashMap;

public class EditCenterAdapter extends FragmentStateAdapter {

    // Objects
    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    // Vars
    private String type;

    public EditCenterAdapter(@NonNull FragmentActivity fragment, String type) {
        super(fragment);
        this.type = type;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (type.equals("personal_clinic")) {
            if (position == 0) {
                Fragment fragment = new EditCenterTabDetailFragment();
                hashMap.put(position, fragment);
                return fragment;
            } else {
                return null;
            }
        } else {
            switch (position) {
                case 0: {
                    Fragment fragment = new EditCenterTabDetailFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } case 1: {
                    Fragment fragment = new EditCenterTabAvatarFragment();
                    hashMap.put(position, fragment);
                    return fragment;
                } default:
                    return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (type.equals("personal_clinic"))
            return 1;
        else
            return 2;
    }

}