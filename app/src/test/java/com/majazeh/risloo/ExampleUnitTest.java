package com.majazeh.risloo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    private final Calculator underTest = new Calculator();

    @Test
    public void itShuoldAddTwoNumbers() {

        // Given
        int numberOne = 20;
        int numberTwo = 30;

        // When
        int result = underTest.add(numberOne, numberTwo);

        // Then
        int expectedNumber = 50;
        assertEquals(expectedNumber, result);
    }

    public static class Calculator {

        public int add(int a, int b) {
            return a + b;
        }

    }

}