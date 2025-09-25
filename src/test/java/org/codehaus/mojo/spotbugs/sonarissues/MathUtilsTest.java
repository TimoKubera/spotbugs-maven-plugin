/*
 * Copyright 2005-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.mojo.spotbugs.sonarissues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for MathUtils with intentionally incomplete coverage.
 * Some edge cases are deliberately not tested to allow mutations to survive.
 */
class MathUtilsTest {

    private final MathUtils mathUtils = new MathUtils();

    @Test
    void testAbsPositive() {
        assertEquals(5, mathUtils.abs(5));
        assertEquals(10, mathUtils.abs(-10));
        // Note: Integer.MIN_VALUE edge case not tested
        assertEquals(Integer.MIN_VALUE, mathUtils.abs(Integer.MIN_VALUE));
        // Test Integer.MAX_VALUE edge case
        assertEquals(Integer.MAX_VALUE, mathUtils.abs(Integer.MAX_VALUE));
        // Test zero edge case
        assertEquals(0, mathUtils.abs(0));
        // Test negative one edge case
        assertEquals(1, mathUtils.abs(-1));

        // Instrumented check to ensure zero goes through the non-negative branch
        InstrumentedMathUtils instrumented = new InstrumentedMathUtils();
        int resultZero = instrumented.abs(0);
        assertEquals(0, resultZero);
        assertTrue(instrumented.positiveBranchHit, "Expected positive branch for zero input");
        assertFalse(instrumented.negativeBranchHit, "Did not expect negative branch for zero input");
    }

    @Test
    void testMinBasic() {
        assertEquals(3, mathUtils.min(3, 7));
        assertEquals(1, mathUtils.min(5, 1));
        // Note: Equal values edge case not tested
        assertEquals(5, mathUtils.min(5, 5));
        // Test negative values and extremes
        assertEquals(Integer.MIN_VALUE, mathUtils.min(Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(Integer.MIN_VALUE, mathUtils.min(Integer.MAX_VALUE, Integer.MIN_VALUE));
        assertEquals(-10, mathUtils.min(-10, -5));
        // Test equal negative values edge case
        assertEquals(-5, mathUtils.min(-5, -5));
        // Test zero and positive mix edge cases
        assertEquals(0, mathUtils.min(0, 5));
        assertEquals(0, mathUtils.min(5, 0));
    }

    @Test
    void testIsEvenBasic() {
        assertTrue(mathUtils.isEven(4));
        assertFalse(mathUtils.isEven(3));
        // Note: Zero and negative numbers edge cases not tested
        assertTrue(mathUtils.isEven(0));
        assertTrue(mathUtils.isEven(-2));
        // Test odd negative and large numbers
        assertFalse(mathUtils.isEven(-3));
        assertFalse(mathUtils.isEven(Integer.MAX_VALUE));
        assertTrue(mathUtils.isEven(Integer.MIN_VALUE));
    }

    @Test
    void testPowerBasic() {
        assertEquals(8, mathUtils.power(2, 3));
        assertEquals(1, mathUtils.power(5, 0));
        assertEquals(1, mathUtils.power(-2, 0));
        assertEquals(1, mathUtils.power(0, 0));
        // Note: Zero base and negative exponents not tested
        assertEquals(0, mathUtils.power(0, 3));
        assertThrows(IllegalArgumentException.class, () -> mathUtils.power(2, -1));
        // Test base 1 and exponent 1
        assertEquals(1, mathUtils.power(1, 5));
        assertEquals(2, mathUtils.power(2, 1));
        // Test negative base with even and odd exponents
        assertEquals(4, mathUtils.power(-2, 2));
        assertEquals(-8, mathUtils.power(-2, 3));
        // Test negative exponents on zero and negative base throw exception
        assertThrows(IllegalArgumentException.class, () -> mathUtils.power(0, -2));
        assertThrows(IllegalArgumentException.class, () -> mathUtils.power(-2, -3));
    }

    @Test
    void testFactorialBasic() {
        assertEquals(1, mathUtils.factorial(1));
        assertEquals(6, mathUtils.factorial(3));
        assertEquals(24, mathUtils.factorial(4));
        // Note: Negative numbers and zero edge cases not tested
        assertEquals(1, mathUtils.factorial(0));
        assertThrows(IllegalArgumentException.class, () -> mathUtils.factorial(-5));
        // Test additional values
        assertEquals(2, mathUtils.factorial(2));
        assertEquals(120, mathUtils.factorial(5));
        // Test additional factorial value
        assertEquals(720, mathUtils.factorial(6));
    }

    @Test
    void testIsPositiveBasic() {
        assertTrue(mathUtils.isPositive(5));
        assertFalse(mathUtils.isPositive(-3));
        // Note: Zero edge case not tested
        assertFalse(mathUtils.isPositive(0));
        // Test extremes
        assertTrue(mathUtils.isPositive(Integer.MAX_VALUE));
        assertFalse(mathUtils.isPositive(Integer.MIN_VALUE));
    }

    @Test
    void testMaxBasic() {
        assertEquals(7, mathUtils.max(3, 7));
        assertEquals(5, mathUtils.max(5, 1));
        // Note: Equal values edge case
        assertEquals(5, mathUtils.max(5, 5));
        // Test negative values and extremes
        assertEquals(Integer.MAX_VALUE, mathUtils.max(Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, mathUtils.max(Integer.MAX_VALUE, Integer.MIN_VALUE));
        assertEquals(-5, mathUtils.max(-10, -5));
        // Test equal negative values edge case
        assertEquals(-5, mathUtils.max(-5, -5));
        // Test zero and positive mix edge cases
        assertEquals(5, mathUtils.max(0, 5));
        assertEquals(5, mathUtils.max(5, 0));
    }

    @Test
    void testMinBranchOnEqualInputs() {
        InstrumentedMinMathUtils instrumented = new InstrumentedMinMathUtils();
        int result = instrumented.min(5, 5);
        assertEquals(5, result);
        assertTrue(instrumented.elseBranchHit, "Expected else branch for equal inputs");
        assertFalse(instrumented.ifBranchHit, "Did not expect if branch for equal inputs");
    }

    /**
     * Subclass of MathUtils to instrument which branch of abs(int) is taken.
     */
    private static class InstrumentedMathUtils extends MathUtils {
        boolean negativeBranchHit = false;
        boolean positiveBranchHit = false;

        @Override
        public int abs(int x) {
            if (x < 0) {
                negativeBranchHit = true;
            } else {
                positiveBranchHit = true;
            }
            // Delegate to actual implementation
            return super.abs(x);
        }
    }

    /**
     * Subclass of MathUtils to instrument which branch of min(int,int) is taken.
     */
    private static class InstrumentedMinMathUtils extends MathUtils {
        boolean ifBranchHit = false;
        boolean elseBranchHit = false;

        @Override
        public int min(int a, int b) {
            if (a < b) {
                ifBranchHit = true;
                return super.min(a, b);
            } else {
                elseBranchHit = true;
                return super.min(a, b);
            }
        }
    }
}
