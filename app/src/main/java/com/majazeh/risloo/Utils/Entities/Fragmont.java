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
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateBillFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateClientReportFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateDocumentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreatePlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreatePracticeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateRoomUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateSessionUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateTreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.ReserveScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditTreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.BanksFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.BillingsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.BulkSamplesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.CasesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterPlatformsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterTagsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterUsersFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.CentersFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.ClientReportsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.DocumentsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.DownloadsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.PaymentsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomPlatformsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomTagsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomUsersFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.SamplesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.ScalesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.SessionsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.TreasuriesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.UsersFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.AccountingFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.BillFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.BulkSampleFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.CaseFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.CenterFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.DashboardFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.MeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.ReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.RoomFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.SampleFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.SessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.TreasuryFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.UserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditCenterTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditCenterTabDetailFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditUserTabCryptoFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditUserTabPasswordFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditUserTabPersonalFragment;
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

    /*
    ---------- Intialize ----------
    */

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

        else if (fragment instanceof CreateBillFragment)
            return fragment;

        else if (fragment instanceof CreateCaseFragment)
            return fragment;

        else if (fragment instanceof CreateCaseUserFragment)
            return fragment;

        else if (fragment instanceof CreateCenterFragment)
            return fragment;

        else if (fragment instanceof CreateCenterUserFragment)
            return fragment;

        else if (fragment instanceof CreateClientReportFragment)
            return fragment;

        else if (fragment instanceof CreateDocumentFragment)
            return fragment;

        else if (fragment instanceof CreatePlatformFragment)
            return fragment;

        else if (fragment instanceof CreatePracticeFragment)
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

        else if (fragment instanceof BanksFragment)
            return fragment;
        
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

        else if (fragment instanceof DownloadsFragment)
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

        else if (fragment instanceof RoomsFragment)
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
    ---------- Create Schedule & Session ----------
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