package com.majazeh.risloo.Views.Adapters.Tab;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.majazeh.risloo.Views.Fragments.Edit.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionTimeFragment;

public class EditSessionAdapter extends FragmentStatePagerAdapter {

    private final SparseArray<Fragment> registeredFragments = new SparseArray<>();

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public EditSessionAdapter(@NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EditSessionTimeFragment();
            case 1:
                return new EditSessionSessionFragment();
            case 2:
                return new EditSessionPaymentFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}