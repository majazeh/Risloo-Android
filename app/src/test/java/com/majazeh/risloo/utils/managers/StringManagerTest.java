package com.majazeh.risloo.utils.managers;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringManagerTest {

    /*
    ---------- Bracing's ----------
    */

    @Test
    public void testBracingInt() {

        // Give
        int value = 5;

        // When
        String result = StringManager.bracing(value);

        // Then
        String expected = "(5)";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testBracingString() {

        // Give
        String value = "5";

        // When
        String result = StringManager.bracing(value);

        // Then
        String expected = "(5)";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    /*
    ---------- Fix's ----------
    */

    @Test
    public void testPrefixChar() {

        // Give
        String value = "madagascer";
        char target = 'g';

        // When
        String result = StringManager.prefix(value, target);

        // Then
        String expected = "mada";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testPrefixInt() {

        // Give
        String value = "madagascer";
        int target = 4;

        // When
        String result = StringManager.prefix(value, target);

        // Then
        String expected = "mada";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSuffixChar() {

        // Give
        String value = "madagascer";
        char target = 'g';

        // When
        String result = StringManager.suffix(value, target);

        // Then
        String expected = "ascer";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSuffixInt() {

        // Give
        String value = "madagascer";
        int target = 5;

        // When
        String result = StringManager.suffix(value, target);

        // Then
        String expected = "ascer";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    /*
    ---------- Change's ----------
    */

    @Test
    public void testReplace() {

        // Give
        String value = "my name is amir and i am 28 years old.";
        String oldText = "amir";
        String newText = "mohannad";

        // When
        String result = StringManager.replace(value, oldText, newText);

        // Then
        String expected = "my name is mohannad and i am 28 years old.";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    /*
    ---------- Seperate's ----------
    */

    @Test
    public void testSeperatePlus() {

        // Give
        String value = "1000000";

        // When
        String result = StringManager.seperatePlus(value);

        // Then
        String expected = "1,000,000";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSeperateMinus() {

        // Give
        String value = "-1000000";

        // When
        String result = StringManager.seperateMinus(value);

        // Then
        String expected = "(1,000,000)";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    /*
    ---------- Adjust's ----------
    */

    @Test
    public void testAdjustProfile() {

        // Give
        String value = "profile_hard_png";

        // When
        String result = StringManager.adjustProfile(value);

        // Then
        String expected = "HARD PNG";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testAdjustMobile() {

        // Give
        String value = "989123456789";

        // When
        String result = StringManager.adjustMobile(value);

        // Then
        String expected = "09123456789";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    /*
    ---------- Char's ----------
    */

    // -------------------- Single

    @Test
    public void testCharFirst() {

        // Give
        String value = "Hello";

        // When
        String result = StringManager.charFirst(value);

        // Then
        String expected = "H";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testCharLast() {

        // Give
        String value = "Hello";

        // When
        String result = StringManager.charLast(value);

        // Then
        String expected = "o";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    // -------------------- Multi

    @Test
    public void testCharsFirst() {

        // Give
        String value = "Hello World";

        // When
        String result = StringManager.charsFirst(value);

        // Then
        String expected = "HW";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testCharsLast() {

        // Give
        String value = "Hello World";

        // When
        String result = StringManager.charsLast(value);

        // Then
        String expected = "od";

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    /*
    ---------- Compare's ----------
    */

    @Test
    public void testCompareVersionNames() {

        // Give
        String oldVersionName = "1.0.0";
        String newVersionName = "1.1.0";

        // When
        int result = StringManager.compareVersionNames(oldVersionName, newVersionName);

        // Then
        int expected = 1;

        // Assert
        assertThat(result).isEqualTo(expected);
    }

}