package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.NavigationAuthDirections;
import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.NavigationTestDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.Objects;

public class Navigatoon {

    // Objects
    private final NavController navController;
    private NavGraph navGraph;

    /*
    ---------- Intialize ----------
    */

    public Navigatoon(@NonNull Activity activity, @NonNull NavHostFragment navHostFragment) {
        navController = navHostFragment.getNavController();

        if (activity instanceof AuthActivity)
            navGraph = navController.getNavInflater().inflate(R.navigation.navigation_auth);
        else if (activity instanceof MainActivity)
            navGraph = navController.getNavInflater().inflate(R.navigation.navigation_main);
        else if (activity instanceof TestActivity)
            navGraph = navController.getNavInflater().inflate(R.navigation.navigation_test);
    }

    /*
    ---------- Directions ----------
    */

    // -------------------- Auth

    public void navigateToAuthLoginFragment() {
        NavDirections action = NavigationAuthDirections.actionGlobalAuthLoginFragment();
        navController.navigate(action);
    }

    public void navigateToAuthPasswordChangeFragment(TypeModel typeModel) {
        NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordChangeFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToAuthPasswordFragment(TypeModel typeModel) {
        NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToAuthPasswordRecoverFragment() {
        NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordRecoverFragment();
        navController.navigate(action);
    }

    public void navigateToAuthPinFragment(TypeModel typeModel) {
        NavDirections action = NavigationAuthDirections.actionGlobalAuthPinFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToAuthRegisterFragment() {
        NavDirections action = NavigationAuthDirections.actionGlobalAuthRegisterFragment();
        navController.navigate(action);
    }

    public void navigateToAuthSerialFragment() {
        NavDirections action = NavigationAuthDirections.actionGlobalAuthSerialFragment();
        navController.navigate(action);
    }

    // -------------------- Create

    public void navigateToCreateBillFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateBillFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateCaseFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateCaseFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateCaseUserFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateCaseUserFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateCenterFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateCenterFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateCenterUserFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateCenterUserFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateClientReportFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateClientReportFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateDocumentFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateDocumentFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreatePlatformFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreatePlatformFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreatePracticeFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreatePracticeFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateRoomFragment(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateRoomFragment(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateRoomUserFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateRoomUserFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateSampleFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateSampleFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateScheduleFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateScheduleFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateSessionFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateSessionFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateSessionUserFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateSessionUserFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateTreasuryFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateTreasuryFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCreateUserFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCreateUserFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToReserveScheduleFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalReserveScheduleFragment(typeModel);
        navController.navigate(action);
    }

    // -------------------- Edit

    public void navigateToEditCenterFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToEditCenterUserFragment(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalEditCenterUserFragment(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToEditPlatformFragment(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalEditPlatformFragment(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToEditSessionFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalEditSessionFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToEditTreasuryFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalEditTreasuryFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToEditUserFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalEditUserFragment(typeModel);
        navController.navigate(action);
    }

    // -------------------- Index

    public void navigateToBalancesFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalBalancesFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToBanksFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalBanksFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToBillingsFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalBillingsFragment();
        navController.navigate(action);
    }

    public void navigateToBulkSamplesFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalBulkSamplesFragment();
        navController.navigate(action);
    }

    public void navigateToCasesFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalCasesFragment();
        navController.navigate(action);
    }

    public void navigateToCenterPlatformsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCenterSchedulesFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCentersFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalCentersFragment();
        navController.navigate(action);
    }

    public void navigateToCenterTagsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCenterTagsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCenterUsersFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToClientReportsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalClientReportsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCommissionsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCommissionsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToDocumentsFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalDocumentsFragment();
        navController.navigate(action);
    }

    public void navigateToDownloadsFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalDownloadsFragment();
        navController.navigate(action);
    }

    public void navigateToPaymentsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToRoomPlatformsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalRoomPlatformsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToRoomSchedulesFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalRoomSchedulesFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToRoomsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalRoomsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToRoomTagsFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalRoomTagsFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToRoomUsersFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalRoomUsersFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToSamplesFragment(String chainId, String[] samplesIds) {
        NavDirections action = NavigationMainDirections.actionGlobalSamplesFragment(chainId, samplesIds);
        navController.navigate(action);
    }

    public void navigateToScalesFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalScalesFragment();
        navController.navigate(action);
    }

    public void navigateToSessionsFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalSessionsFragment();
        navController.navigate(action);
    }

    public void navigateToTreasuriesFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalTreasuriesFragment();
        navController.navigate(action);
    }

    public void navigateToUsersFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalUsersFragment();
        navController.navigate(action);
    }

    // -------------------- Show

    public void navigateToAccountingFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalAccountingFragment();
        navController.navigate(action);
    }

    public void navigateToBillFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalBillFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToBulkSampleFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalBulkSampleFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCaseFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCaseFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCenterAccountingFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCenterAccountingFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToCenterFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalCenterFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToDashboardFragment() {
        NavDirections action = NavigationMainDirections.actionGlobalDashboardFragment();
        navController.navigate(action);
    }

    public void navigateToFolderFragment(String folderName) {
        NavDirections action = NavigationMainDirections.actionGlobalFolderFragment(folderName);
        navController.navigate(action);
    }

    public void navigateToMeFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalMeFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToReferenceFragment(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToRoomFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalRoomFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToSampleFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalSampleFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToSessionFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalSessionFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToTreasuryFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalTreasuryFragment(typeModel);
        navController.navigate(action);
    }

    public void navigateToUserFragment(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalUserFragment(typeModel);
        navController.navigate(action);
    }

    // -------------------- Test

    public void navigateToTestChainFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestChainFragment();
        navController.navigate(action);
    }

    public void navigateToTestDescriptionFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestDescriptionFragment();
        navController.navigate(action);
    }

    public void navigateToTestEndFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestEndFragment();
        navController.navigate(action);
    }

    public void navigateToTestEntityFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestEntityFragment();
        navController.navigate(action);
    }

    public void navigateToTestDescriptiveFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestDescriptiveFragment();
        navController.navigate(action);
    }

    public void navigateToTestOptionalFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestOptionalFragment();
        navController.navigate(action);
    }

    public void navigateToTestPictoralFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestPictoralFragment();
        navController.navigate(action);
    }

    public void navigateToTestPrerequisiteFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestPrerequisiteFragment();
        navController.navigate(action);
    }

    public void navigateToTestPsyDescFragment() {
        NavDirections action = NavigationTestDirections.actionGlobalTestPsyDescFragment();
        navController.navigate(action);
    }

    // -------------------- Backstack

    public void navigateUp() {
        navController.navigateUp();
    }

    /*
    ---------- Setters ----------
    */

    public void setStartDestinationId(int fragment) {
        navGraph.setStartDestination(fragment);
        navController.setGraph(navGraph);
    }

    /*
    ---------- Getters ----------
    */

    public int getStartDestinationId() {
        return navGraph.getStartDestination();
    }

    public int getCurrentDestinationId() {
        return Objects.requireNonNull(navController.getCurrentDestination()).getId();
    }

    public int getBackstackDestinationId() {
        return Objects.requireNonNull(navController.getPreviousBackStackEntry()).getDestination().getId();
    }

    public NavController getNavController() {
        return navController;
    }

}