package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Tab.EditUserAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserCryptoFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserPasswordFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserPersonalFragment;

import java.util.HashMap;

public class EditUserAdapter extends FragmentStateAdapter {

    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public EditUserAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new EditUserPersonalFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 1: {
                Fragment fragment = new EditUserPasswordFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 2: {
                Fragment fragment = new EditUserAvatarFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 3: {
                Fragment fragment = new EditUserCryptoFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}