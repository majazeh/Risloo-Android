package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Create.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionTimeFragment;

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
            case 0:
                Fragment fragment0 = new CreateSessionTimeFragment();
                hashMap.put(position, fragment0);
                return fragment0;
            case 1:
                Fragment fragment1 = new CreateSessionSessionFragment();
                hashMap.put(position, fragment1);
                return fragment1;
            case 2:
                Fragment fragment2 = new CreateSessionPaymentFragment();
                hashMap.put(position, fragment2);
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}