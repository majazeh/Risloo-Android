package com.majazeh.risloo.Views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.fragments.main.tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.Views.fragments.main.tab.EditUserTabCryptoFragment;
import com.majazeh.risloo.Views.fragments.main.tab.EditUserTabPasswordFragment;
import com.majazeh.risloo.Views.fragments.main.tab.EditUserTabPersonalFragment;

import java.util.HashMap;

public class EditUserAdapter extends FragmentStateAdapter {

    // Objects
    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public EditUserAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new EditUserTabPersonalFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 1: {
                Fragment fragment = new EditUserTabPasswordFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 2: {
                Fragment fragment = new EditUserTabAvatarFragment();
                hashMap.put(position, fragment);
                return fragment;
            } case 3: {
                Fragment fragment = new EditUserTabCryptoFragment();
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