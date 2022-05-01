package com.majazeh.risloo.utils.managers;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VersionManagerTest {

    /*
    ---------- Test's ----------
    */

    @Test
    public void testVersionCode() {

        // Give
        Context context = ApplicationProvider.getApplicationContext();

        // When
        int result = VersionManager.versionCode(context);

        // Then
        int expected = 9;

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testVersionName() {

        // Give
        Context context = ApplicationProvider.getApplicationContext();

        // When
        String result = VersionManager.versionName(context);

        // Then
        String expected = "1.7.0-debug";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testVersionNamePrefix() {

        // Give
        Context context = ApplicationProvider.getApplicationContext();

        // When
        String result = VersionManager.versionNamePrefix(context);

        // Then
        String expected = "1.7.0";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testVersionNameDesc() {

        // Give
        Context context = ApplicationProvider.getApplicationContext();

        // When
        String result = VersionManager.versionNameDesc(context);

        // Then
        String expected = "نسخه 1.7.0";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

}