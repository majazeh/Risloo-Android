package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.majazeh.risloo.Views.Fragments.Edit.EditAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCryptoFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditPasswordFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditPersonalFragment;

public class EditAccountAdapter extends FragmentStatePagerAdapter {

    public EditAccountAdapter(@NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EditPersonalFragment();
            case 1:
                return new EditPasswordFragment();
            case 2:
                return new EditAvatarFragment();
            case 3:
                return new EditCryptoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}