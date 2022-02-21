package com.majazeh.risloo.views.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabAvatar;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabCrypto;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabPassword;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabPersonal;

import java.util.HashMap;

public class AdapterEditUser extends FragmentStateAdapter {

    // Objects
    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public AdapterEditUser(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new FragmentEditUserTabPersonal();
                hashMap.put(position, fragment);
                return fragment;
            } case 1: {
                Fragment fragment = new FragmentEditUserTabPassword();
                hashMap.put(position, fragment);
                return fragment;
            } case 2: {
                Fragment fragment = new FragmentEditUserTabAvatar();
                hashMap.put(position, fragment);
                return fragment;
            } case 3: {
                Fragment fragment = new FragmentEditUserTabCrypto();
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