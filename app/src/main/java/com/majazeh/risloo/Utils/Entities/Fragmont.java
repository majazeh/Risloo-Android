package com.majazeh.risloo.Utils.Entities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.Views.Fragments.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateDocumentFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreatePracticeFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateReportFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateTreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateUserFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditTreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomUsersFragment;
import com.majazeh.risloo.Views.Fragments.Show.SampleFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserCryptoFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserPasswordFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserPersonalFragment;

public class Fragmont {

    // Objects
    private final NavHostFragment navHostFragment;

    public Fragmont(@NonNull NavHostFragment navHostFragment) {
        this.navHostFragment = navHostFragment;
    }

    public Fragment getCurrent() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        // -------------------- Create

        if (fragment instanceof CreateCaseFragment)
            return fragment;

        else if (fragment instanceof CreateCaseUserFragment)
            return fragment;

        else if (fragment instanceof CreateCenterFragment)
            return fragment;

        else if (fragment instanceof CreateCenterUserFragment)
            return fragment;

        else if (fragment instanceof CreateDocumentFragment)
            return fragment;

        else if (fragment instanceof CreatePracticeFragment)
            return fragment;

        else if (fragment instanceof CreateReportFragment)
            return fragment;

        else if (fragment instanceof CreateRoomFragment)
            return fragment;

        else if (fragment instanceof CreateRoomUserFragment)
            return fragment;

        else if (fragment instanceof CreateSampleFragment)
            return fragment;

        else if (fragment instanceof CreateScheduleFragment)
            return fragment;

        else if (fragment instanceof CreateSessionFragment)
            return fragment;

        else if (fragment instanceof CreateTreasuryFragment)
            return fragment;

        else if (fragment instanceof CreateUserFragment)
            return fragment;

        // -------------------- Edit

        else if (fragment instanceof EditCenterFragment)
            return fragment;

        else if (fragment instanceof EditCenterUserFragment)
            return fragment;

        else if (fragment instanceof EditSessionFragment)
            return fragment;

        else if (fragment instanceof EditTreasuryFragment)
            return fragment;

        else if (fragment instanceof EditUserFragment)
            return fragment;

        // -------------------- Index

        else if (fragment instanceof CenterUsersFragment)
            return fragment;

        else if (fragment instanceof CenterSchedulesFragment)
            return fragment;

        else if (fragment instanceof RoomUsersFragment)
            return fragment;

        else if (fragment instanceof RoomSchedulesFragment)
            return fragment;

        // -------------------- Show

        else if (fragment instanceof SampleFragment)
            return fragment;

        return null;
    }

    public Fragment getChild() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        // -------------------- Create

        if (fragment instanceof CreateScheduleFragment) {
            Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (childFragment != null)
                if (childFragment instanceof CreateScheduleTimeFragment)
                    return childFragment;
                else if (childFragment instanceof CreateScheduleReferenceFragment)
                    return childFragment;
                else if (childFragment instanceof CreateScheduleSessionFragment)
                    return childFragment;
                else if (childFragment instanceof CreateSchedulePaymentFragment)
                    return childFragment;

        } else if (fragment instanceof CreateSessionFragment) {
            Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (childFragment != null)
                if (childFragment instanceof CreateSessionTimeFragment)
                    return childFragment;
                else if (childFragment instanceof CreateSessionSessionFragment)
                    return childFragment;
                else if (childFragment instanceof CreateSessionPaymentFragment)
                    return childFragment;

            // -------------------- Edit

        } else if (fragment instanceof EditCenterFragment) {
            Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (childFragment != null)
                if (childFragment instanceof EditCenterDetailFragment)
                    return childFragment;
                else if (childFragment instanceof EditCenterAvatarFragment)
                    return childFragment;
        }

        else if (fragment instanceof EditSessionFragment) {
            Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (childFragment != null)
                if (childFragment instanceof EditSessionTimeFragment)
                    return childFragment;
                else if (childFragment instanceof EditSessionSessionFragment)
                    return childFragment;
                else if (childFragment instanceof EditSessionPaymentFragment)
                    return childFragment;
        }

        else if (fragment instanceof EditUserFragment) {
            Fragment childFragment = ((EditUserFragment) fragment).adapter.hashMap.get(((EditUserFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (childFragment != null)
                if (childFragment instanceof EditUserPersonalFragment)
                    return childFragment;
                else if (childFragment instanceof EditUserPasswordFragment)
                    return childFragment;
                else if (childFragment instanceof EditUserAvatarFragment)
                    return childFragment;
                else if (childFragment instanceof EditUserCryptoFragment)
                    return childFragment;
        }

        return null;
    }

    public Fragment getPayment() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment paymentFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(3);
            if (paymentFragment != null)
                if (paymentFragment instanceof CreateSchedulePaymentFragment)
                    return paymentFragment;

        } else if (fragment instanceof CreateSessionFragment) {
            Fragment paymentFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(2);
            if (paymentFragment != null)
                if (paymentFragment instanceof CreateSessionPaymentFragment)
                    return paymentFragment;

        } else if (fragment instanceof EditSessionFragment) {
            Fragment paymentFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(2);
            if (paymentFragment != null)
                if (paymentFragment instanceof EditSessionPaymentFragment)
                    return paymentFragment;
        }

        return null;
    }

}