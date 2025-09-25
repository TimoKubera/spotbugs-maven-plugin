/*
 * Copyright 2005-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the \"License\");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an \"AS IS\" BASIS,
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
        // Note: Zero base and negative exponents not tested
        assertEquals(0, mathUtils.power(0, 3));
        assertThrows(IllegalArgumentException.class, () -> mathUtils.power(2, -1));
        // Test base 1 and exponent 1
        assertEquals(1, mathUtils.power(1, 5));
        assertEquals(2, mathUtils.power(2, 1));
        // Test negative base with even and odd exponents
        assertEquals(4, mathUtils.power(-2, 2));
        assertEquals(-8, mathUtils.power(-2, 3));
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
}
