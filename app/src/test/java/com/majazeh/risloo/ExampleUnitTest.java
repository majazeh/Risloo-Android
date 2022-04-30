package com.majazeh.risloo;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExampleUnitTest {

    @Test
    public void testAddFunc() {

        // Give
        int numberOne = 20;
        int numberTwo = 30;

        // When
        Calculator calculator = new Calculator();
        int result = calculator.add(numberOne, numberTwo);

        // Then
        int expected = 50;

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    public static class Calculator {

        public int add(int a, int b) {
            return a + b;
        }

    }

}