package com.majazeh.risloo;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testAppContext() {

        // Give
        Context context = ApplicationProvider.getApplicationContext();

        // When
        String result = context.getPackageName();

        // Then
        String expected = "com.majazeh.risloo.debug";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

}