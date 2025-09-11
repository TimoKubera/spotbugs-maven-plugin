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
package org.codehaus.mojo.spotbugs

import org.junit.Test
import org.junit.Ignore
import org.junit.Before
import org.junit.After
import static org.junit.Assert.*

/**
 * Test class containing SonarQube issues for Groovy code.
 */
class SonarQubeIssuesGroovyTest {

    // Issue: Inconsistent naming convention
    def test_instance

    // Issue: Method setup could be @Before
    void setup() {
        test_instance = new SonarQubeIssuesGroovy()
    }

    // Issue: Test without assertions
    @Test
    void testWithoutAssertions() {
        test_instance.buildLargeString()
        // No assertions
    }

    // Issue: Ignored test
    @Ignore
    @Test
    void ignoredTest() {
        assertTrue(true)
    }

    // Issue: Sleep in test
    @Test
    void testWithSleep() {
        Thread.sleep(1000) // Bad practice
        assertTrue(test_instance.redundantIf(true))
    }

    // Issue: System.out in test
    @Test
    void testWithPrinting() {
        System.out.println("Running test")
        assertEquals(1, 1)
    }

    // Issue: Hard-coded path in test
    @Test
    void testWithHardcodedPath() {
        def file = new File("C:/temp/testfile.txt")
        assertFalse(file.exists())
    }

    // Issue: Too many assertions in one test
    @Test
    void testTooManyAssertions() {
        assertNotNull(test_instance)
        assertTrue(test_instance.redundantIf(true))
        assertFalse(test_instance.redundantIf(false))
        assertEquals("", test_instance.readFile("nonexistent.txt"))
        assertTrue(test_instance.complexCondition(true, true, false, false, false))
        assertFalse(test_instance.complexCondition(false, false, false, false, false))
    }

    // Issue: Empty test
    @Test
    void emptyTest() {
        // Empty test
    }

    // Issue: Assertion with literal boolean
    @Test
    void testAssertLiteralBoolean() {
        assertEquals(true, true) // Should use assertTrue
    }

    // Issue: Testing multiple concerns
    @Test
    void testMultipleConcerns() {
        // Test build string
        def largeString = test_instance.buildLargeString()
        assertTrue(largeString.length() > 0)

        // Test complex condition
        assertTrue(test_instance.complexCondition(true, true, false, false, false))

        // Test redundant if
        assertTrue(test_instance.redundantIf(true))
    }

    // Issue: Duplicated test code
    @Test
    void testDuplicate1() {
        def result = test_instance.buildLargeString()
        assertTrue(result.length() > 0)
        assertTrue(result.contains("Item 1"))
    }

    // Issue: Duplicated test code
    @Test
    void testDuplicate2() {
        def result = test_instance.buildLargeString()
        assertTrue(result.length() > 0)
        assertTrue(result.contains("Item 1"))
    }

    // Issue: Test with flaky assertion (depends on execution environment)
    @Test
    void testEnvironmentDependent() {
        def availableProcessors = Runtime.runtime.availableProcessors()
        assertTrue("System should have at least 4 processors", availableProcessors >= 4)
    }

    // Issue: Testing implementation details
    @Test
    void testImplementationDetails() {
        // Accessing internal implementation details
        assertEquals("admin", SonarQubeIssuesGroovy.DB_USERNAME)
        assertEquals("admin123", SonarQubeIssuesGroovy.DB_PASSWORD)
    }

    // Issue: Trivial test
    @Test
    void testTrivial() {
        def list = [1, 2, 3]
        assertEquals(3, list.size())
    }

    // Issue: Using try-catch in tests
    @Test
    void testWithTryCatch() {
        try {
            test_instance.processString(null)
            fail("Should have thrown exception")
        } catch (Exception e) {
            // Any exception is accepted, which is too broad
            assertTrue(true)
        }
    }

    // Issue: Random test
    @Test
    void testRandom() {
        def random = new Random()
        def value = random.nextInt(100)
        assertTrue(value >= 0 && value < 100)
    }

    // Issue: Test with side effects (creates files)
    @Test
    void testWithSideEffects() {
        def file = new File("temp_test.txt")
        file.text = "Test content"
        assertTrue(file.exists())
        // Not cleaning up after test
    }

    // Issue: Unnecessary mocking
    @Test
    void testWithUnnecessaryMocking() {
        def mockList = [
            size: { -> return 0 },
            isEmpty: { -> return true }
        ] as List

        assertTrue(mockList.isEmpty())
        assertEquals(0, mockList.size())
    }

    // Issue: Useless assertion
    @Test
    void testUselessAssertion() {
        def obj = new Object()
        assertNotNull(obj)
    }

    // Issue: Testing trivial getter
    @Test
    void testTrivialGetter() {
        def person = new Person(name: "John", age: 30)
        assertEquals("John", person.name)
        assertEquals(30, person.age)
    }

    // Issue: Using Thread in tests
    @Test
    void testWithThread() {
        def result = "not set"
        def thread = new Thread({ result = "set" })
        thread.start()
        thread.join()
        assertEquals("set", result)
    }

    // Issue: No teardown
    void cleanup() {
        // Should be annotated with @After
        test_instance = null
    }
}

// Helper class for tests
class Person {
    String name
    int age
}
