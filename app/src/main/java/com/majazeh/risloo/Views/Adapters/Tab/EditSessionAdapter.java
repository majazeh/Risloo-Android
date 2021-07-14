package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;

import java.util.HashMap;

public class EditSessionAdapter extends FragmentStateAdapter {

    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public EditSessionAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
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
                Fragment fragment = new EditSessionPaymentFragment();
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