package com.majazeh.risloo.Utils.Entities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.Views.Fragments.Auth.AuthLoginFragment;
import com.majazeh.risloo.Views.Fragments.Auth.AuthPasswordChangeFragment;
import com.majazeh.risloo.Views.Fragments.Auth.AuthPasswordFragment;
import com.majazeh.risloo.Views.Fragments.Auth.AuthPasswordRecoverFragment;
import com.majazeh.risloo.Views.Fragments.Auth.AuthPinFragment;
import com.majazeh.risloo.Views.Fragments.Auth.AuthRegisterFragment;
import com.majazeh.risloo.Views.Fragments.Auth.AuthSerialFragment;
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
import com.majazeh.risloo.Views.Fragments.Create.ReserveScheduleFragment;
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
import com.majazeh.risloo.Views.Fragments.Index.CenterTagsFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragment;
import com.majazeh.risloo.Views.Fragments.Index.CentersFragment;
import com.majazeh.risloo.Views.Fragments.Index.ClientReportsFragment;
import com.majazeh.risloo.Views.Fragments.Index.DocumentsFragment;
import com.majazeh.risloo.Views.Fragments.Index.PaymentsFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomPlatformsFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomTagsFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomUsersFragment;
import com.majazeh.risloo.Views.Fragments.Index.SamplesFragment;
import com.majazeh.risloo.Views.Fragments.Index.ScalesFragment;
import com.majazeh.risloo.Views.Fragments.Index.SessionsFragment;
import com.majazeh.risloo.Views.Fragments.Index.TreasuriesFragment;
import com.majazeh.risloo.Views.Fragments.Index.UsersFragment;
import com.majazeh.risloo.Views.Fragments.Show.AccountingFragment;
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
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterTabDetailFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserTabCryptoFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserTabPasswordFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserTabPersonalFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestChainFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestDescriptionFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestEndFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestEntityFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestOptionalFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestPictoralFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestPrerequisiteFragment;
import com.majazeh.risloo.Views.Fragments.Test.TestPsyDescFragment;

public class Fragmont {

    // Objects
    private final NavHostFragment navHostFragment;

    public Fragmont(@NonNull NavHostFragment navHostFragment) {
        this.navHostFragment = navHostFragment;
    }

    /*
    ---------- Current & Child ----------
    */

    public Fragment getCurrent() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        // -------------------- Auth

        if (fragment instanceof AuthLoginFragment)
            return fragment;

        else if (fragment instanceof AuthPasswordChangeFragment)
            return fragment;

        else if (fragment instanceof AuthPasswordFragment)
            return fragment;

        else if (fragment instanceof AuthPasswordRecoverFragment)
            return fragment;

        else if (fragment instanceof AuthPinFragment)
            return fragment;

        else if (fragment instanceof AuthRegisterFragment)
            return fragment;

        else if (fragment instanceof AuthSerialFragment)
            return fragment;

        // -------------------- Create

        else if (fragment instanceof CreateCaseFragment)
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

        else if (fragment instanceof ReserveScheduleFragment)
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

        else if (fragment instanceof CenterTagsFragment)
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

        else if (fragment instanceof RoomTagsFragment)
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

        else if (fragment instanceof AccountingFragment)
            return fragment;

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

        // -------------------- Test

        else if (fragment instanceof TestChainFragment)
            return fragment;

        else if (fragment instanceof TestDescriptionFragment)
            return fragment;

        else if (fragment instanceof TestEndFragment)
            return fragment;

        else if (fragment instanceof TestEntityFragment)
            return fragment;

        else if (fragment instanceof TestOptionalFragment)
            return fragment;

        else if (fragment instanceof TestPictoralFragment)
            return fragment;

        else if (fragment instanceof TestPrerequisiteFragment)
            return fragment;

        else if (fragment instanceof TestPsyDescFragment)
            return fragment;

        return null;
    }

    public Fragment getChild() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        // -------------------- Create

        if (fragment instanceof CreateScheduleFragment) {
            Fragment child = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof CreateScheduleTabTimeFragment)
                    return child;
                else if (child instanceof CreateScheduleTabReferenceFragment)
                    return child;
                else if (child instanceof CreateScheduleTabSessionFragment)
                    return child;
                else if (child instanceof CreateScheduleTabPlatformFragment)
                    return child;
                else if (child instanceof CreateScheduleTabPaymentFragment)
                    return child;
        }

        else if (fragment instanceof CreateSessionFragment) {
            Fragment child = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof CreateSessionTabTimeFragment)
                    return child;
                else if (child instanceof CreateSessionTabSessionFragment)
                    return child;
                else if (child instanceof CreateSessionTabPlatformFragment)
                    return child;
                else if (child instanceof CreateSessionTabPaymentFragment)
                    return child;
        }

        // -------------------- Edit

        else if (fragment instanceof EditCenterFragment) {
            Fragment child = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof EditCenterTabDetailFragment)
                    return child;
                else if (child instanceof EditCenterTabAvatarFragment)
                    return child;
        }

        else if (fragment instanceof EditSessionFragment) {
            Fragment child = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof EditSessionTabTimeFragment)
                    return child;
                else if (child instanceof EditSessionTabReferenceFragment)
                    return child;
                else if (child instanceof EditSessionTabSessionFragment)
                    return child;
                else if (child instanceof EditSessionTabPlatformFragment)
                    return child;
                else if (child instanceof EditSessionTabPaymentFragment)
                    return child;
        }

        else if (fragment instanceof EditUserFragment) {
            Fragment child = ((EditUserFragment) fragment).adapter.hashMap.get(((EditUserFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof EditUserTabPersonalFragment)
                    return child;
                else if (child instanceof EditUserTabPasswordFragment)
                    return child;
                else if (child instanceof EditUserTabAvatarFragment)
                    return child;
                else if (child instanceof EditUserTabCryptoFragment)
                    return child;
        }

        return null;
    }

    /*
    ---------- CreateSchedule & CreateSession ----------
    */

    public Fragment getTime() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment time = ((CreateScheduleFragment) fragment).adapter.hashMap.get(0);
            if (time != null)
                if (time instanceof CreateScheduleTabTimeFragment)
                    return time;
        }

        else if (fragment instanceof CreateSessionFragment) {
            Fragment time = ((CreateSessionFragment) fragment).adapter.hashMap.get(0);
            if (time != null)
                if (time instanceof CreateSessionTabTimeFragment)
                    return time;
        }

        return null;
    }

    public Fragment getReference() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment reference = ((CreateScheduleFragment) fragment).adapter.hashMap.get(1);
            if (reference != null)
                if (reference instanceof CreateScheduleTabReferenceFragment)
                    return reference;
        }

        return null;
    }

    public Fragment getSession() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment session = ((CreateScheduleFragment) fragment).adapter.hashMap.get(2);
            if (session != null)
                if (session instanceof CreateScheduleTabSessionFragment)
                    return session;
        }

        else if (fragment instanceof CreateSessionFragment) {
            Fragment session = ((CreateSessionFragment) fragment).adapter.hashMap.get(1);
            if (session != null)
                if (session instanceof CreateSessionTabSessionFragment)
                    return session;
        }

        return null;
    }

    public Fragment getPlatform() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment platform = ((CreateScheduleFragment) fragment).adapter.hashMap.get(3);
            if (platform != null)
                if (platform instanceof CreateScheduleTabPlatformFragment)
                    return platform;
        }

        else if (fragment instanceof CreateSessionFragment) {
            Fragment platform = ((CreateSessionFragment) fragment).adapter.hashMap.get(2);
            if (platform != null)
                if (platform instanceof CreateSessionTabPlatformFragment)
                    return platform;
        }

        return null;
    }

    public Fragment getPayment() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof CreateScheduleFragment) {
            Fragment payment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(4);
            if (payment != null)
                if (payment instanceof CreateScheduleTabPaymentFragment)
                    return payment;
        }

        else if (fragment instanceof CreateSessionFragment) {
            Fragment payment = ((CreateSessionFragment) fragment).adapter.hashMap.get(3);
            if (payment != null)
                if (payment instanceof CreateSessionTabPaymentFragment)
                    return payment;
        }

        return null;
    }

    /*
    ---------- EditSession ----------
    */

    public Fragment getEditSessionTabTime() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment child = ((EditSessionFragment) fragment).adapter.hashMap.get(0);

            if (child != null)
                if (child instanceof EditSessionTabTimeFragment)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTabReference(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment child;

            if (hasCase)
                return null;
            else
                child = ((EditSessionFragment) fragment).adapter.hashMap.get(1);

            if (child != null)
                if (child instanceof EditSessionTabReferenceFragment)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTabSession(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment child;

            if (hasCase)
                child = ((EditSessionFragment) fragment).adapter.hashMap.get(1);
            else
                child = ((EditSessionFragment) fragment).adapter.hashMap.get(2);

            if (child != null)
                if (child instanceof EditSessionTabSessionFragment)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTabPlatform(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment child;

            if (hasCase)
                child = ((EditSessionFragment) fragment).adapter.hashMap.get(2);
            else
                child = ((EditSessionFragment) fragment).adapter.hashMap.get(3);

            if (child != null)
                if (child instanceof EditSessionTabPlatformFragment)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTagPayment(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EditSessionFragment) {
            Fragment child;

            if (hasCase)
                child = ((EditSessionFragment) fragment).adapter.hashMap.get(3);
            else
                child = ((EditSessionFragment) fragment).adapter.hashMap.get(4);

            if (child != null)
                if (child instanceof EditSessionTabPaymentFragment)
                    return child;
        }

        return null;
    }

}