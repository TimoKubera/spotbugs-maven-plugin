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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Intentionally incomplete tests for Calculator class.
 * This will allow many mutations to survive, which is what we want for testing.
 */
public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
        // Missing edge cases: negative numbers, zero, etc.
        assertEquals(0, calculator.add(2, -2));
        assertEquals(-5, calculator.add(-2, -3));
        assertEquals(2, calculator.add(2, 0));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
        // Missing edge cases
        assertEquals(-1, calculator.subtract(-3, -2));
        assertEquals(3, calculator.subtract(3, 0));
        assertEquals(-5, calculator.subtract(-2, 3));
        assertEquals(5, calculator.subtract(3, -2));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
        // Missing edge cases: zero, negative numbers, etc.
        assertEquals(0, calculator.multiply(2, 0));
        assertEquals(-6, calculator.multiply(-2, 3));
        assertEquals(6, calculator.multiply(-2, -3));
    }

    @Test
    public void testDivide() {
        assertEquals(2.0, calculator.divide(4, 2), 0.001);

        // Test division by zero
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(5, 0));
        // Missing other edge cases
        assertEquals(1.5, calculator.divide(3, 2), 0.001);
        assertEquals(-2.0, calculator.divide(-4, 2), 0.001);
        assertEquals(-2.0, calculator.divide(4, -2), 0.001);
        assertEquals(2.0, calculator.divide(-4, -2), 0.001);
    }

    @Test
    public void testMax() {
        assertEquals(5, calculator.max(3, 5));
        // Kill boundary mutations
        assertEquals(5, calculator.max(5, 3));  // Reverse order
        assertEquals(5, calculator.max(5, 5));  // Equal values
        assertEquals(-1, calculator.max(-1, -2));
        assertEquals(0, calculator.max(0, -1));
    }

    @Test
    public void testIsPrime() {
        assertTrue(calculator.isPrime(7));
        assertFalse(calculator.isPrime(4));
        // Kill boundary mutations
        assertFalse(calculator.isPrime(1));  // Edge case: boundary check
        assertFalse(calculator.isPrime(0));  // Edge case: boundary check
        assertTrue(calculator.isPrime(2));   // Edge case: smallest prime
        assertFalse(calculator.isPrime(9));  // Perfect square: kills modulus mutation
        assertFalse(calculator.isPrime(-7));  // Negative input
        assertFalse(calculator.isPrime(15));  // Composite non-square
    }

    // Intentionally NOT testing average() method - allows mutations to survive

    @Test
    public void testFibonacci() {
        assertEquals(0, calculator.fibonacci(0));
        assertEquals(1, calculator.fibonacci(1));
        assertEquals(8, calculator.fibonacci(6));
        // Kill more mutations
        assertEquals(1, calculator.fibonacci(2));  // Kill boundary mutation for n == 1
        assertEquals(2, calculator.fibonacci(3));  // Kill additional conditional mutations
        assertThrows(IllegalArgumentException.class, () -> calculator.fibonacci(-1));
        assertEquals(13, calculator.fibonacci(7));  // Additional mutation killing
        assertEquals(55, calculator.fibonacci(10));
    }

    @Test
    public void testGcd() {
        // Very minimal test - only test basic valid case
        // This will kill some mutations but leave many others as SURVIVED
        assertEquals(5, calculator.gcd(10, 15));
        // NOT testing edge cases:
        // - (0,0), (a,0), (0,b), negative numbers, etc.
        // This allows most mutations to survive!
        // Additional edge cases
        assertEquals(10, calculator.gcd(10, 0));
        assertEquals(15, calculator.gcd(0, 15));
        assertEquals(5, calculator.gcd(-10, 15));
        assertEquals(5, calculator.gcd(10, -15));
        assertEquals(5, calculator.gcd(-10, -15));
        assertThrows(IllegalArgumentException.class, () -> calculator.gcd(0, 0));
        assertEquals(1, calculator.gcd(7, 13));
        assertEquals(1, calculator.gcd(13, 7));
    }

    @Test
    public void testIsValidInteger() {
        assertTrue(calculator.isValidInteger("123"));
        assertFalse(calculator.isValidInteger("abc"));
        // Kill boundary mutations
        assertFalse(calculator.isValidInteger(null));   // Null check
        assertFalse(calculator.isValidInteger(""));     // Empty string
        assertTrue(calculator.isValidInteger("-123"));  // Negative numbers
        assertTrue(calculator.isValidInteger("+123"));
        assertFalse(calculator.isValidInteger(" 123"));  // Leading space
        assertFalse(calculator.isValidInteger("12.3"));  // Decimal number
    }

    @Test
    public void testAverage() {
        assertEquals(2.0, calculator.average(1, 2, 3), 0.001);
        assertEquals(1.5, calculator.average(1, 2), 0.001);
        assertEquals(3.0, calculator.average(1, 3, 5), 0.001);
        assertEquals(5.0, calculator.average(5), 0.001);
        assertEquals(0.0, calculator.average(-2, 2), 0.001);
        assertEquals(0.0, calculator.average(0, 0), 0.001);
        // Test null and no args
        assertThrows(IllegalArgumentException.class, () -> calculator.average());
        assertThrows(IllegalArgumentException.class, () -> calculator.average((int[]) null));
    }
}
