package com.majazeh.risloo.utils.entities;

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
import com.majazeh.risloo.views.activities.ActivityAuth;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.activities.ActivityTest;
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

        if (activity instanceof ActivityAuth)
            navGraph = navController.getNavInflater().inflate(R.navigation.navigation_auth);
        else if (activity instanceof ActivityMain)
            navGraph = navController.getNavInflater().inflate(R.navigation.navigation_main);
        else if (activity instanceof ActivityTest)
            navGraph = navController.getNavInflater().inflate(R.navigation.navigation_test);
    }

    /*
    ---------- Directions ----------
    */

    // -------------------- Auth

    public void navigateToFragmentAuthLogin() {
        NavDirections action = NavigationAuthDirections.actionGlobalFragmentAuthLogin();
        navController.navigate(action);
    }

    public void navigateToFragmentAuthPasswordChange(TypeModel typeModel) {
        NavDirections action = NavigationAuthDirections.actionGlobalFragmentAuthPasswordChange(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentAuthPassword(TypeModel typeModel) {
        NavDirections action = NavigationAuthDirections.actionGlobalFragmentAuthPassword(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentAuthPasswordRecover() {
        NavDirections action = NavigationAuthDirections.actionGlobalFragmentAuthPasswordRecover();
        navController.navigate(action);
    }

    public void navigateToFragmentAuthPin(TypeModel typeModel) {
        NavDirections action = NavigationAuthDirections.actionGlobalFragmentAuthPin(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentAuthRegister() {
        NavDirections action = NavigationAuthDirections.actionGlobalFragmentAuthRegister();
        navController.navigate(action);
    }

    public void navigateToFragmentAuthSerial() {
        NavDirections action = NavigationAuthDirections.actionGlobalFragmentAuthSerial();
        navController.navigate(action);
    }

    // -------------------- Create

    public void navigateToFragmentCreateBill(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateBill(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateCase(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateCase(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateCaseUser(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateCaseUser(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateCenter(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateCenter(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateCenterUser(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateCenterUser(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateClientReport(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateClientReport(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateDocument(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateDocument(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreatePlatform(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreatePlatform(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreatePractice(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreatePractice(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateRoom(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateRoom(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateRoomUser(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateRoomUser(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateSample(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateSample(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateSchedule(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateSchedule(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateSession(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateSession(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateSessionUser(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateSessionUser(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateTreasury(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateTreasury(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCreateUser(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCreateUser(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentReserveSchedule(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentReserveSchedule(typeModel);
        navController.navigate(action);
    }

    // -------------------- Edit

    public void navigateToFragmentEditCenter(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentEditCenter(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentEditCenterUser(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentEditCenterUser(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentEditPlatform(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentEditPlatform(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentEditSession(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentEditSession(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentEditTreasury(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentEditTreasury(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentEditUser(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentEditUser(typeModel);
        navController.navigate(action);
    }

    // -------------------- Index

    public void navigateToFragmentBalances(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentBalances(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentBanks(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentBanks(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentBillings() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentBillings();
        navController.navigate(action);
    }

    public void navigateToFragmentBulkSamples() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentBulkSamples();
        navController.navigate(action);
    }

    public void navigateToFragmentCases() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCases();
        navController.navigate(action);
    }

    public void navigateToFragmentCenterPlatforms(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCenterPlatforms(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCenterSchedules(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCenterSchedules(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCenters() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCenters();
        navController.navigate(action);
    }

    public void navigateToFragmentCenterTags(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCenterTags(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCenterUsers(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCenterUsers(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentClientReports(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentClientReports(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCommissions(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCommissions(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentDocuments() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentDocuments();
        navController.navigate(action);
    }

    public void navigateToFragmentDownloads() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentDownloads();
        navController.navigate(action);
    }

    public void navigateToFragmentPayments(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentPayments(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentRoomPlatforms(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentRoomPlatforms(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentRoomSchedules(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentRoomSchedules(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentRooms(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentRooms(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentRoomTags(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentRoomTags(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentRoomUsers(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentRoomUsers(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentSamples(String chainId, String[] samplesIds) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentSamples(chainId, samplesIds);
        navController.navigate(action);
    }

    public void navigateToFragmentScales() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentScales();
        navController.navigate(action);
    }

    public void navigateToFragmentSessions() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentSessions();
        navController.navigate(action);
    }

    public void navigateToFragmentTreasuries() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentTreasuries();
        navController.navigate(action);
    }

    public void navigateToFragmentUsers() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentUsers();
        navController.navigate(action);
    }

    // -------------------- Show

    public void navigateToFragmentAccounting() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentAccounting();
        navController.navigate(action);
    }

    public void navigateToFragmentBill(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentBill(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentBulkSample(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentBulkSample(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCase(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCase(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCenterAccounting(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCenterAccounting(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentCenter(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentCenter(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentDashboard() {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentDashboard();
        navController.navigate(action);
    }

    public void navigateToFragmentFolder(String folderName) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentFolder(folderName);
        navController.navigate(action);
    }

    public void navigateToFragmentMe(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentMe(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentReference(TypeModel centerModel, TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentReference(centerModel, typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentRoom(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentRoom(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentSample(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentSample(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentSession(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentSession(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentTreasury(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentTreasury(typeModel);
        navController.navigate(action);
    }

    public void navigateToFragmentUser(TypeModel typeModel) {
        NavDirections action = NavigationMainDirections.actionGlobalFragmentUser(typeModel);
        navController.navigate(action);
    }

    // -------------------- Test

    public void navigateToFragmentTestChain() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestChain();
        navController.navigate(action);
    }

    public void navigateToFragmentTestDescription() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestDescription();
        navController.navigate(action);
    }

    public void navigateToFragmentTestEnd() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestEnd();
        navController.navigate(action);
    }

    public void navigateToFragmentTestEntity() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestEntity();
        navController.navigate(action);
    }

    public void navigateToFragmentTestDescriptive() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestDescriptive();
        navController.navigate(action);
    }

    public void navigateToFragmentTestOptional() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestOptional();
        navController.navigate(action);
    }

    public void navigateToFragmentTestPictoral() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestPictoral();
        navController.navigate(action);
    }

    public void navigateToFragmentTestPrerequisite() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestPrerequisite();
        navController.navigate(action);
    }

    public void navigateToFragmentTestPsyDesc() {
        NavDirections action = NavigationTestDirections.actionGlobalFragmentTestPsyDesc();
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