package com.majazeh.risloo.Views.Adapters.Tab;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSchedulePaymentFragment;

public class CreateScheduleAdapter extends FragmentStatePagerAdapter {

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

    public CreateScheduleAdapter(@NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CreateScheduleTimeFragment();
            case 1:
                return new CreateScheduleReferenceFragment();
            case 2:
                return new CreateScheduleSessionFragment();
            case 3:
                return new CreateSchedulePaymentFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}