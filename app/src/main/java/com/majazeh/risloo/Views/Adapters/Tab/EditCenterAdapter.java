package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.majazeh.risloo.Views.Fragments.Edit.EditCenterAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterDetailFragment;

public class EditCenterAdapter extends FragmentStatePagerAdapter {

    public EditCenterAdapter(@NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EditCenterDetailFragment();
            case 1:
                return new EditCenterAvatarFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}