package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;

import java.util.HashMap;

public class CreateSessionAdapter extends FragmentStateAdapter {

    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public CreateSessionAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new CreateSessionTimeFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 1: {
                Fragment fragment = new CreateSessionSessionFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            case 2: {
                Fragment fragment = new CreateSessionPaymentFragment();
                hashMap.put(position, fragment);
                return fragment;
            }
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}