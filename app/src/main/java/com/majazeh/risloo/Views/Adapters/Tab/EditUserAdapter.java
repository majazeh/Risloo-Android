package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Edit.EditAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCryptoFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditPasswordFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditPersonalFragment;

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
            case 0:
                Fragment fragment0 = new EditPersonalFragment();
                hashMap.put(position, fragment0);
                return fragment0;
            case 1:
                Fragment fragment1 = new EditPasswordFragment();
                hashMap.put(position, fragment1);
                return fragment1;
            case 2:
                Fragment fragment2 = new EditAvatarFragment();
                hashMap.put(position, fragment2);
                return fragment2;
            case 3:
                Fragment fragment3 = new EditCryptoFragment();
                hashMap.put(position, fragment3);
                return fragment3;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}