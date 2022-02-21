package com.majazeh.risloo.utils.entities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.views.fragments.auth.FragmentAuthLogin;
import com.majazeh.risloo.views.fragments.auth.FragmentAuthPasswordChange;
import com.majazeh.risloo.views.fragments.auth.FragmentAuthPassword;
import com.majazeh.risloo.views.fragments.auth.FragmentAuthPasswordRecover;
import com.majazeh.risloo.views.fragments.auth.FragmentAuthPin;
import com.majazeh.risloo.views.fragments.auth.FragmentAuthRegister;
import com.majazeh.risloo.views.fragments.auth.FragmentAuthSerial;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateBill;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCase;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCaseUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCenter;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCenterUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateClientReport;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateDocument;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreatePlatform;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreatePractice;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateRoom;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateRoomUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSample;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSchedule;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSession;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSessionUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateTreasury;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentReserveSchedule;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditCenter;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditCenterUser;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditPlatform;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditSession;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditTreasury;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditUser;
import com.majazeh.risloo.views.fragments.main.index.FragmentBalances;
import com.majazeh.risloo.views.fragments.main.index.FragmentBanks;
import com.majazeh.risloo.views.fragments.main.index.FragmentBillings;
import com.majazeh.risloo.views.fragments.main.index.FragmentBulkSamples;
import com.majazeh.risloo.views.fragments.main.index.FragmentCases;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterPlatforms;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterSchedules;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterTags;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterUsers;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenters;
import com.majazeh.risloo.views.fragments.main.index.FragmentClientReports;
import com.majazeh.risloo.views.fragments.main.index.FragmentCommissions;
import com.majazeh.risloo.views.fragments.main.index.FragmentDocuments;
import com.majazeh.risloo.views.fragments.main.index.FragmentDownloads;
import com.majazeh.risloo.views.fragments.main.index.FragmentPayments;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomPlatforms;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomSchedules;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomTags;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomUsers;
import com.majazeh.risloo.views.fragments.main.index.FragmentRooms;
import com.majazeh.risloo.views.fragments.main.index.FragmentSamples;
import com.majazeh.risloo.views.fragments.main.index.FragmentScales;
import com.majazeh.risloo.views.fragments.main.index.FragmentSessions;
import com.majazeh.risloo.views.fragments.main.index.FragmentTreasuries;
import com.majazeh.risloo.views.fragments.main.index.FragmentUsers;
import com.majazeh.risloo.views.fragments.main.show.FragmentAccounting;
import com.majazeh.risloo.views.fragments.main.show.FragmentBill;
import com.majazeh.risloo.views.fragments.main.show.FragmentBulkSample;
import com.majazeh.risloo.views.fragments.main.show.FragmentCase;
import com.majazeh.risloo.views.fragments.main.show.FragmentCenterAccounting;
import com.majazeh.risloo.views.fragments.main.show.FragmentCenter;
import com.majazeh.risloo.views.fragments.main.show.FragmentDashboard;
import com.majazeh.risloo.views.fragments.main.show.FragmentFolder;
import com.majazeh.risloo.views.fragments.main.show.FragmentMe;
import com.majazeh.risloo.views.fragments.main.show.FragmentReference;
import com.majazeh.risloo.views.fragments.main.show.FragmentRoom;
import com.majazeh.risloo.views.fragments.main.show.FragmentSample;
import com.majazeh.risloo.views.fragments.main.show.FragmentSession;
import com.majazeh.risloo.views.fragments.main.show.FragmentTreasury;
import com.majazeh.risloo.views.fragments.main.show.FragmentUser;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabReference;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditCenterTabAvatar;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditCenterTabDetail;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabReference;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabAvatar;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabCrypto;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabPassword;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditUserTabPersonal;
import com.majazeh.risloo.views.fragments.test.FragmentTestChain;
import com.majazeh.risloo.views.fragments.test.FragmentTestDescription;
import com.majazeh.risloo.views.fragments.test.FragmentTestDescriptive;
import com.majazeh.risloo.views.fragments.test.FragmentTestEnd;
import com.majazeh.risloo.views.fragments.test.FragmentTestEntity;
import com.majazeh.risloo.views.fragments.test.FragmentTestOptional;
import com.majazeh.risloo.views.fragments.test.FragmentTestPictoral;
import com.majazeh.risloo.views.fragments.test.FragmentTestPrerequisite;
import com.majazeh.risloo.views.fragments.test.FragmentTestPsyDesc;

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

        if (fragment instanceof FragmentAuthLogin)
            return fragment;

        else if (fragment instanceof FragmentAuthPasswordChange)
            return fragment;

        else if (fragment instanceof FragmentAuthPassword)
            return fragment;

        else if (fragment instanceof FragmentAuthPasswordRecover)
            return fragment;

        else if (fragment instanceof FragmentAuthPin)
            return fragment;

        else if (fragment instanceof FragmentAuthRegister)
            return fragment;

        else if (fragment instanceof FragmentAuthSerial)
            return fragment;

        // -------------------- Create

        else if (fragment instanceof FragmentCreateBill)
            return fragment;

        else if (fragment instanceof FragmentCreateCase)
            return fragment;

        else if (fragment instanceof FragmentCreateCaseUser)
            return fragment;

        else if (fragment instanceof FragmentCreateCenter)
            return fragment;

        else if (fragment instanceof FragmentCreateCenterUser)
            return fragment;

        else if (fragment instanceof FragmentCreateClientReport)
            return fragment;

        else if (fragment instanceof FragmentCreateDocument)
            return fragment;

        else if (fragment instanceof FragmentCreatePlatform)
            return fragment;

        else if (fragment instanceof FragmentCreatePractice)
            return fragment;

        else if (fragment instanceof FragmentCreateRoom)
            return fragment;

        else if (fragment instanceof FragmentCreateRoomUser)
            return fragment;

        else if (fragment instanceof FragmentCreateSample)
            return fragment;

        else if (fragment instanceof FragmentCreateSchedule)
            return fragment;

        else if (fragment instanceof FragmentCreateSession)
            return fragment;

        else if (fragment instanceof FragmentCreateSessionUser)
            return fragment;

        else if (fragment instanceof FragmentCreateTreasury)
            return fragment;

        else if (fragment instanceof FragmentCreateUser)
            return fragment;

        else if (fragment instanceof FragmentReserveSchedule)
            return fragment;

        // -------------------- Edit

        else if (fragment instanceof FragmentEditCenter)
            return fragment;

        else if (fragment instanceof FragmentEditCenterUser)
            return fragment;

        else if (fragment instanceof FragmentEditPlatform)
            return fragment;

        else if (fragment instanceof FragmentEditSession)
            return fragment;

        else if (fragment instanceof FragmentEditTreasury)
            return fragment;

        else if (fragment instanceof FragmentEditUser)
            return fragment;

        // -------------------- Index

        else if (fragment instanceof FragmentBalances)
            return fragment;

        else if (fragment instanceof FragmentBanks)
            return fragment;
        
        else if (fragment instanceof FragmentBillings)
            return fragment;

        else if (fragment instanceof FragmentBulkSamples)
            return fragment;

        else if (fragment instanceof FragmentCases)
            return fragment;

        else if (fragment instanceof FragmentCenterPlatforms)
            return fragment;

        else if (fragment instanceof FragmentCenterSchedules)
            return fragment;

        else if (fragment instanceof FragmentCenters)
            return fragment;

        else if (fragment instanceof FragmentCenterTags)
            return fragment;

        else if (fragment instanceof FragmentCenterUsers)
            return fragment;

        else if (fragment instanceof FragmentClientReports)
            return fragment;

        else if (fragment instanceof FragmentCommissions)
            return fragment;

        else if (fragment instanceof FragmentDocuments)
            return fragment;

        else if (fragment instanceof FragmentDownloads)
            return fragment;

        else if (fragment instanceof FragmentPayments)
            return fragment;

        else if (fragment instanceof FragmentRoomPlatforms)
            return fragment;

        else if (fragment instanceof FragmentRoomSchedules)
            return fragment;

        else if (fragment instanceof FragmentRoomTags)
            return fragment;

        else if (fragment instanceof FragmentRoomUsers)
            return fragment;

        else if (fragment instanceof FragmentRooms)
            return fragment;

        else if (fragment instanceof FragmentSamples)
            return fragment;

        else if (fragment instanceof FragmentScales)
            return fragment;

        else if (fragment instanceof FragmentSessions)
            return fragment;

        else if (fragment instanceof FragmentTreasuries)
            return fragment;

        else if (fragment instanceof FragmentUsers)
            return fragment;

        // -------------------- Show

        else if (fragment instanceof FragmentAccounting)
            return fragment;

        else if (fragment instanceof FragmentBill)
            return fragment;

        else if (fragment instanceof FragmentBulkSample)
            return fragment;

        else if (fragment instanceof FragmentCase)
            return fragment;

        else if (fragment instanceof FragmentCenterAccounting)
            return fragment;

        else if (fragment instanceof FragmentCenter)
            return fragment;

        else if (fragment instanceof FragmentDashboard)
            return fragment;

        else if (fragment instanceof FragmentFolder)
            return fragment;

        else if (fragment instanceof FragmentMe)
            return fragment;

        else if (fragment instanceof FragmentReference)
            return fragment;

        else if (fragment instanceof FragmentRoom)
            return fragment;

        else if (fragment instanceof FragmentSample)
            return fragment;

        else if (fragment instanceof FragmentSession)
            return fragment;

        else if (fragment instanceof FragmentTreasury)
            return fragment;

        else if (fragment instanceof FragmentUser)
            return fragment;

        // -------------------- Test

        else if (fragment instanceof FragmentTestChain)
            return fragment;

        else if (fragment instanceof FragmentTestDescription)
            return fragment;

        else if (fragment instanceof FragmentTestEnd)
            return fragment;

        else if (fragment instanceof FragmentTestEntity)
            return fragment;

        else if (fragment instanceof FragmentTestDescriptive)
            return fragment;

        else if (fragment instanceof FragmentTestOptional)
            return fragment;

        else if (fragment instanceof FragmentTestPictoral)
            return fragment;

        else if (fragment instanceof FragmentTestPrerequisite)
            return fragment;

        else if (fragment instanceof FragmentTestPsyDesc)
            return fragment;

        return null;
    }

    public Fragment getChild() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        // -------------------- Create

        if (fragment instanceof FragmentCreateSchedule) {
            Fragment child = ((FragmentCreateSchedule) fragment).adapter.hashMap.get(((FragmentCreateSchedule) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof FragmentCreateScheduleTabTime)
                    return child;
                else if (child instanceof FragmentCreateScheduleTabReference)
                    return child;
                else if (child instanceof FragmentCreateScheduleTabSession)
                    return child;
                else if (child instanceof FragmentCreateScheduleTabPlatform)
                    return child;
                else if (child instanceof FragmentCreateScheduleTabPayment)
                    return child;
        }

        else if (fragment instanceof FragmentCreateSession) {
            Fragment child = ((FragmentCreateSession) fragment).adapter.hashMap.get(((FragmentCreateSession) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof FragmentCreateSessionTabTime)
                    return child;
                else if (child instanceof FragmentCreateSessionTabSession)
                    return child;
                else if (child instanceof FragmentCreateSessionTabPlatform)
                    return child;
                else if (child instanceof FragmentCreateSessionTabPayment)
                    return child;
        }

        // -------------------- Edit

        else if (fragment instanceof FragmentEditCenter) {
            Fragment child = ((FragmentEditCenter) fragment).adapter.hashMap.get(((FragmentEditCenter) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof FragmentEditCenterTabDetail)
                    return child;
                else if (child instanceof FragmentEditCenterTabAvatar)
                    return child;
        }

        else if (fragment instanceof FragmentEditSession) {
            Fragment child = ((FragmentEditSession) fragment).adapter.hashMap.get(((FragmentEditSession) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof FragmentEditSessionTabTime)
                    return child;
                else if (child instanceof FragmentEditSessionTabReference)
                    return child;
                else if (child instanceof FragmentEditSessionTabSession)
                    return child;
                else if (child instanceof FragmentEditSessionTabPlatform)
                    return child;
                else if (child instanceof FragmentEditSessionTabPayment)
                    return child;
        }

        else if (fragment instanceof FragmentEditUser) {
            Fragment child = ((FragmentEditUser) fragment).adapter.hashMap.get(((FragmentEditUser) fragment).binding.viewPager.getRoot().getCurrentItem());
            if (child != null)
                if (child instanceof FragmentEditUserTabPersonal)
                    return child;
                else if (child instanceof FragmentEditUserTabPassword)
                    return child;
                else if (child instanceof FragmentEditUserTabAvatar)
                    return child;
                else if (child instanceof FragmentEditUserTabCrypto)
                    return child;
        }

        return null;
    }

    /*
    ---------- Create Schedule & Session ----------
    */

    public Fragment getTime() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentCreateSchedule) {
            Fragment time = ((FragmentCreateSchedule) fragment).adapter.hashMap.get(0);
            if (time != null)
                if (time instanceof FragmentCreateScheduleTabTime)
                    return time;
        }

        else if (fragment instanceof FragmentCreateSession) {
            Fragment time = ((FragmentCreateSession) fragment).adapter.hashMap.get(0);
            if (time != null)
                if (time instanceof FragmentCreateSessionTabTime)
                    return time;
        }

        return null;
    }

    public Fragment getReference() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentCreateSchedule) {
            Fragment reference = ((FragmentCreateSchedule) fragment).adapter.hashMap.get(1);
            if (reference != null)
                if (reference instanceof FragmentCreateScheduleTabReference)
                    return reference;
        }

        return null;
    }

    public Fragment getSession() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentCreateSchedule) {
            Fragment session = ((FragmentCreateSchedule) fragment).adapter.hashMap.get(2);
            if (session != null)
                if (session instanceof FragmentCreateScheduleTabSession)
                    return session;
        }

        else if (fragment instanceof FragmentCreateSession) {
            Fragment session = ((FragmentCreateSession) fragment).adapter.hashMap.get(1);
            if (session != null)
                if (session instanceof FragmentCreateSessionTabSession)
                    return session;
        }

        return null;
    }

    public Fragment getPlatform() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentCreateSchedule) {
            Fragment platform = ((FragmentCreateSchedule) fragment).adapter.hashMap.get(3);
            if (platform != null)
                if (platform instanceof FragmentCreateScheduleTabPlatform)
                    return platform;
        }

        else if (fragment instanceof FragmentCreateSession) {
            Fragment platform = ((FragmentCreateSession) fragment).adapter.hashMap.get(2);
            if (platform != null)
                if (platform instanceof FragmentCreateSessionTabPlatform)
                    return platform;
        }

        return null;
    }

    public Fragment getPayment() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentCreateSchedule) {
            Fragment payment = ((FragmentCreateSchedule) fragment).adapter.hashMap.get(4);
            if (payment != null)
                if (payment instanceof FragmentCreateScheduleTabPayment)
                    return payment;
        }

        else if (fragment instanceof FragmentCreateSession) {
            Fragment payment = ((FragmentCreateSession) fragment).adapter.hashMap.get(3);
            if (payment != null)
                if (payment instanceof FragmentCreateSessionTabPayment)
                    return payment;
        }

        return null;
    }

    /*
    ---------- EditSession ----------
    */

    public Fragment getEditSessionTabTime() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentEditSession) {
            Fragment child = ((FragmentEditSession) fragment).adapter.hashMap.get(0);

            if (child != null)
                if (child instanceof FragmentEditSessionTabTime)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTabReference(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentEditSession) {
            Fragment child;

            if (hasCase)
                return null;
            else
                child = ((FragmentEditSession) fragment).adapter.hashMap.get(1);

            if (child != null)
                if (child instanceof FragmentEditSessionTabReference)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTabSession(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentEditSession) {
            Fragment child;

            if (hasCase)
                child = ((FragmentEditSession) fragment).adapter.hashMap.get(1);
            else
                child = ((FragmentEditSession) fragment).adapter.hashMap.get(2);

            if (child != null)
                if (child instanceof FragmentEditSessionTabSession)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTabPlatform(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentEditSession) {
            Fragment child;

            if (hasCase)
                child = ((FragmentEditSession) fragment).adapter.hashMap.get(2);
            else
                child = ((FragmentEditSession) fragment).adapter.hashMap.get(3);

            if (child != null)
                if (child instanceof FragmentEditSessionTabPlatform)
                    return child;
        }

        return null;
    }

    public Fragment getEditSessionTagPayment(boolean hasCase) {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof FragmentEditSession) {
            Fragment child;

            if (hasCase)
                child = ((FragmentEditSession) fragment).adapter.hashMap.get(3);
            else
                child = ((FragmentEditSession) fragment).adapter.hashMap.get(4);

            if (child != null)
                if (child instanceof FragmentEditSessionTabPayment)
                    return child;
        }

        return null;
    }

}