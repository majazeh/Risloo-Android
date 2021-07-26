package com.majazeh.risloo.Utils.Entities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.Views.Fragments.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateDocumentFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreatePlatformFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreatePracticeFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateReportFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateTreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateUserFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditTreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.Views.Fragments.Index.BillingsFragment;
import com.majazeh.risloo.Views.Fragments.Index.BulkSamplesFragment;
import com.majazeh.risloo.Views.Fragments.Index.CasesFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterPlatformsFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragment;
import com.majazeh.risloo.Views.Fragments.Index.CentersFragment;
import com.majazeh.risloo.Views.Fragments.Index.ClientReportsFragment;
import com.majazeh.risloo.Views.Fragments.Index.DocumentsFragment;
import com.majazeh.risloo.Views.Fragments.Index.PaymentsFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomPlatformsFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomUsersFragment;
import com.majazeh.risloo.Views.Fragments.Index.SamplesFragment;
import com.majazeh.risloo.Views.Fragments.Index.ScalesFragment;
import com.majazeh.risloo.Views.Fragments.Index.SessionsFragment;
import com.majazeh.risloo.Views.Fragments.Index.TreasuriesFragment;
import com.majazeh.risloo.Views.Fragments.Index.UsersFragment;
import com.majazeh.risloo.Views.Fragments.Show.BillFragment;
import com.majazeh.risloo.Views.Fragments.Show.BulkSampleFragment;
import com.majazeh.risloo.Views.Fragments.Show.CaseFragment;
import com.majazeh.risloo.Views.Fragments.Show.CenterFragment;
import com.majazeh.risloo.Views.Fragments.Show.DashboardFragment;
import com.majazeh.risloo.Views.Fragments.Show.MeFragment;
import com.majazeh.risloo.Views.Fragments.Show.ReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Show.RoomFragment;
import com.majazeh.risloo.Views.Fragments.Show.SampleFragment;
import com.majazeh.risloo.Views.Fragments.Show.SessionFragment;
import com.majazeh.risloo.Views.Fragments.Show.TreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Show.UserFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionReferenceFragment;
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

    /*
    ---------- Current ----------
    */

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

        else if (fragment instanceof CreatePlatformFragment)
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

        else if (fragment instanceof CreateSessionUserFragment)
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

        else if (fragment instanceof EditPlatformFragment)
            return fragment;

        else if (fragment instanceof EditSessionFragment)
            return fragment;

        else if (fragment instanceof EditTreasuryFragment)
            return fragment;

        else if (fragment instanceof EditUserFragment)
            return fragment;

        // -------------------- Index

        else if (fragment instanceof BillingsFragment)
            return fragment;

        else if (fragment instanceof BulkSamplesFragment)
            return fragment;

        else if (fragment instanceof CasesFragment)
            return fragment;

        else if (fragment instanceof CenterPlatformsFragment)
            return fragment;

        else if (fragment instanceof CenterSchedulesFragment)
            return fragment;

        else if (fragment instanceof CentersFragment)
            return fragment;

        else if (fragment instanceof CenterUsersFragment)
            return fragment;

        else if (fragment instanceof ClientReportsFragment)
            return fragment;

        else if (fragment instanceof DocumentsFragment)
            return fragment;

        else if (fragment instanceof PaymentsFragment)
            return fragment;

        else if (fragment instanceof RoomPlatformsFragment)
            return fragment;

        else if (fragment instanceof RoomSchedulesFragment)
            return fragment;

        else if (fragment instanceof RoomUsersFragment)
            return fragment;

        else if (fragment instanceof SamplesFragment)
            return fragment;

        else if (fragment instanceof ScalesFragment)
            return fragment;

        else if (fragment instanceof SessionsFragment)
            return fragment;

        else if (fragment instanceof TreasuriesFragment)
            return fragment;

        else if (fragment instanceof UsersFragment)
            return fragment;

        // -------------------- Show

        else if (fragment instanceof BillFragment)
            return fragment;

        else if (fragment instanceof BulkSampleFragment)
            return fragment;

        else if (fragment instanceof CaseFragment)
            return fragment;

        else if (fragment instanceof CenterFragment)
            return fragment;

        else if (fragment instanceof DashboardFragment)
            return fragment;

        else if (fragment instanceof MeFragment)
            return fragment;

        else if (fragment instanceof ReferenceFragment)
            return fragment;

        else if (fragment instanceof RoomFragment)
            return fragment;

        else if (fragment instanceof SampleFragment)
            return fragment;

        else if (fragment instanceof SessionFragment)
            return fragment;

        else if (fragment instanceof TreasuryFragment)
            return fragment;

        else if (fragment instanceof UserFragment)
            return fragment;

        return null;
    }

    public Fragment getChild() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        // -------------------- Create

        if (fragment instanceof CreateScheduleFragment) {
            Fragment child = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof CreateScheduleTimeFragment)
                    return child;
                else if (child instanceof CreateScheduleReferenceFragment)
                    return child;
                else if (child instanceof CreateScheduleSessionFragment)
                    return child;
                else if (child instanceof CreateSchedulePlatformFragment)
                    return child;
                else if (child instanceof CreateSchedulePaymentFragment)
                    return child;

        } else if (fragment instanceof CreateSessionFragment) {
            Fragment child = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof CreateSessionTimeFragment)
                    return child;
                else if (child instanceof CreateSessionSessionFragment)
                    return child;
                else if (child instanceof CreateSessionPlatformFragment)
                    return child;
                else if (child instanceof CreateSessionPaymentFragment)
                    return child;

            // -------------------- Edit

        } else if (fragment instanceof EditCenterFragment) {
            Fragment child = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof EditCenterDetailFragment)
                    return child;
                else if (child instanceof EditCenterAvatarFragment)
                    return child;
        }

        else if (fragment instanceof EditSessionFragment) {
            Fragment child = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof EditSessionTimeFragment)
                    return child;
                else if (child instanceof EditSessionReferenceFragment)
                    return child;
                else if (child instanceof EditSessionSessionFragment)
                    return child;
                else if (child instanceof EditSessionPlatformFragment)
                    return child;
                else if (child instanceof EditSessionPaymentFragment)
                    return child;
        }

        else if (fragment instanceof EditUserFragment) {
            Fragment child = ((EditUserFragment) fragment).adapter.hashMap.get(((EditUserFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof EditUserPersonalFragment)
                    return child;
                else if (child instanceof EditUserPasswordFragment)
                    return child;
                else if (child instanceof EditUserAvatarFragment)
                    return child;
                else if (child instanceof EditUserCryptoFragment)
                    return child;
        }

        return null;
    }

    /*
    ---------- Specefic ----------
    */

    public Fragment getTime() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment time = ((CreateScheduleFragment) fragment).adapter.hashMap.get(0);
            if (time != null)
                if (time instanceof CreateScheduleTimeFragment)
                    return time;

        } else if (fragment instanceof CreateSessionFragment) {
            Fragment time = ((CreateSessionFragment) fragment).adapter.hashMap.get(0);
            if (time != null)
                if (time instanceof CreateSessionTimeFragment)
                    return time;

        } else if (fragment instanceof EditSessionFragment) {
            Fragment time = ((EditSessionFragment) fragment).adapter.hashMap.get(0);
            if (time != null)
                if (time instanceof EditSessionTimeFragment)
                    return time;
        }

        return null;
    }

    public Fragment getReference() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment reference = ((CreateScheduleFragment) fragment).adapter.hashMap.get(1);
            if (reference != null)
                if (reference instanceof CreateScheduleReferenceFragment)
                    return reference;

        } else if (fragment instanceof EditSessionFragment) {
            Fragment reference = ((EditSessionFragment) fragment).adapter.hashMap.get(1);
            if (reference != null)
                if (reference instanceof EditSessionReferenceFragment)
                    return reference;
        }

        return null;
    }

    public Fragment getSession() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment session = ((CreateScheduleFragment) fragment).adapter.hashMap.get(2);
            if (session != null)
                if (session instanceof CreateScheduleSessionFragment)
                    return session;

        } else if (fragment instanceof CreateSessionFragment) {
            Fragment session = ((CreateSessionFragment) fragment).adapter.hashMap.get(1);
            if (session != null)
                if (session instanceof CreateSessionSessionFragment)
                    return session;

        }

        return null;
    }

    public Fragment getPlatform() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment platform = ((CreateScheduleFragment) fragment).adapter.hashMap.get(3);
            if (platform != null)
                if (platform instanceof CreateSchedulePlatformFragment)
                    return platform;

        } else if (fragment instanceof CreateSessionFragment) {
            Fragment platform = ((CreateSessionFragment) fragment).adapter.hashMap.get(2);
            if (platform != null)
                if (platform instanceof CreateSessionPlatformFragment)
                    return platform;

        }

        return null;
    }

    public Fragment getPayment() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment payment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(4);
            if (payment != null)
                if (payment instanceof CreateSchedulePaymentFragment)
                    return payment;

        } else if (fragment instanceof CreateSessionFragment) {
            Fragment payment = ((CreateSessionFragment) fragment).adapter.hashMap.get(3);
            if (payment != null)
                if (payment instanceof CreateSessionPaymentFragment)
                    return payment;

        }

        return null;
    }

    /*
    ---------- EditSession ----------
    */

    public Fragment getSessionEditSession(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment session;

            if (hasCase)
                session = ((EditSessionFragment) fragment).adapter.hashMap.get(1);
            else
                session = ((EditSessionFragment) fragment).adapter.hashMap.get(2);

            if (session != null)
                if (session instanceof EditSessionSessionFragment)
                    return session;
        }

        return null;
    }

    public Fragment getPlatformEditSession(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment platform;

            if (hasCase)
                platform = ((EditSessionFragment) fragment).adapter.hashMap.get(2);
            else
                platform = ((EditSessionFragment) fragment).adapter.hashMap.get(3);

            if (platform != null)
                if (platform instanceof EditSessionPlatformFragment)
                    return platform;
        }

        return null;
    }

    public Fragment getPaymentEditSession(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment payment;

            if (hasCase)
                payment = ((EditSessionFragment) fragment).adapter.hashMap.get(3);
            else
                payment = ((EditSessionFragment) fragment).adapter.hashMap.get(4);

            if (payment != null)
                if (payment instanceof EditSessionPaymentFragment)
                    return payment;
        }

        return null;
    }

}