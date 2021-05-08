package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Tab.EditCenterAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;

import java.util.HashMap;

public class EditCenterAdapter extends FragmentStateAdapter {

    // Vars
    private String center;

    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public EditCenterAdapter(@NonNull FragmentActivity fragment, String center) {
        super(fragment);
        this.center = center;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (center.equals("personal")) {
            if (position == 0) {
                Fragment fragment0 = new EditCenterDetailFragment();
                hashMap.put(position, fragment0);
                return fragment0;
            }
            return null;
        } else {
            switch (position) {
                case 0:
                    Fragment fragment0 = new EditCenterDetailFragment();
                    hashMap.put(position, fragment0);
                    return fragment0;
                case 1:
                    Fragment fragment1 = new EditCenterAvatarFragment();
                    hashMap.put(position, fragment1);
                    return fragment1;
                default:
                    return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (center.equals("personal"))
            return 1;
        else
            return 2;
    }

}