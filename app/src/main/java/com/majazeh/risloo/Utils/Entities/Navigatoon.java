package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.NavigationAuthDirections;
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
    ---------- Voids ----------
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



    // -------------------- Edit



    // -------------------- Index



    // -------------------- Show



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

}